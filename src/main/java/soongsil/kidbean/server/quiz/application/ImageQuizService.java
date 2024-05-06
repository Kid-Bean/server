package soongsil.kidbean.server.quiz.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import soongsil.kidbean.server.global.application.S3Uploader;
import soongsil.kidbean.server.global.vo.S3Info;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.exception.MemberNotFoundException;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.quiz.domain.ImageQuiz;
import soongsil.kidbean.server.quiz.domain.type.QuizCategory;
import soongsil.kidbean.server.quiz.dto.request.QuizSolvedRequest;
import soongsil.kidbean.server.quiz.dto.request.ImageQuizUpdateRequest;
import soongsil.kidbean.server.quiz.dto.request.ImageQuizUploadRequest;
import soongsil.kidbean.server.quiz.dto.response.ImageQuizMemberDetailResponse;
import soongsil.kidbean.server.quiz.dto.response.ImageQuizMemberResponse;
import soongsil.kidbean.server.quiz.dto.response.ImageQuizSolveListResponse;
import soongsil.kidbean.server.quiz.dto.response.ImageQuizSolveResponse;
import soongsil.kidbean.server.quiz.dto.response.ImageQuizSolveScoreResponse;
import soongsil.kidbean.server.quiz.exception.ImageQuizNotFoundException;
import soongsil.kidbean.server.quiz.repository.ImageQuizRepository;

import java.util.List;
import soongsil.kidbean.server.quiz.util.RandNumUtil;

import static soongsil.kidbean.server.member.exception.errorcode.MemberErrorCode.MEMBER_NOT_FOUND;
import static soongsil.kidbean.server.quiz.application.vo.QuizType.IMAGE_QUIZ;
import static soongsil.kidbean.server.quiz.exception.errorcode.QuizErrorCode.IMAGE_QUIZ_NOT_FOUND;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor


public class ImageQuizService {

    private final ImageQuizRepository imageQuizRepository;
    private final MemberRepository memberRepository;
    private final QuizSolvedService quizSolvedService;
    private final S3Uploader s3Uploader;

    private final static String QUIZ_BASE_FOLDER = "quiz/";

    public ImageQuizMemberDetailResponse getImageQuizById(Long memberId, Long quizId) {
        ImageQuiz imageQuiz = imageQuizRepository.findByQuizIdAndMember_MemberId(quizId, memberId)
                .orElseThrow(() -> new ImageQuizNotFoundException(IMAGE_QUIZ_NOT_FOUND));

        return ImageQuizMemberDetailResponse.from(imageQuiz);
    }

    public List<ImageQuizMemberResponse> getAllImageQuizByMember(Long memberId) {
        return imageQuizRepository.findAllByMember_MemberId(memberId).stream()
                .map(ImageQuizMemberResponse::from)
                .toList();
    }

    /**
     * @param quizSolvedRequestList DTO의 List
     * @param memberId              문제를 푼 유저의 id
     * @return 추가된 점수
     */
    @Transactional
    public ImageQuizSolveScoreResponse solveImageQuizzes(List<QuizSolvedRequest> quizSolvedRequestList,
                                                         Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));

        return ImageQuizSolveScoreResponse.scoreFrom(
                quizSolvedService.solveQuizzes(quizSolvedRequestList, member, IMAGE_QUIZ));
    }

    /**
     * 해당 카테고리에서 문제를 랜덤으로 선택
     *
     * @param memberId 문제를 풀 멤버의 id
     * @return 이미지 퀴즈 DTO
     */
    public ImageQuizSolveListResponse selectRandomImageQuizList(Long memberId, Integer quizNum) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));

        int totalQuizNum = getImageQuizCount(member);

        List<ImageQuizSolveResponse> imageQuizSolveResponseList =
                RandNumUtil.generateRandomNumbers(0, totalQuizNum - 1, quizNum).stream()
                        .map(quizIdx -> generateRandomPageWithCategory(member, quizIdx))
                        .map(this::getImageQuizFromPage)
                        .map(ImageQuizSolveResponse::from)
                        .toList();

        return new ImageQuizSolveListResponse(imageQuizSolveResponseList);
    }

    @Transactional
    public void uploadImageQuiz(ImageQuizUploadRequest request, Long memberId, MultipartFile multipartFile) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));

        ImageQuiz imageQuiz = request.toImageQuiz(member);
        S3Info s3Info = uploadS3Info(multipartFile, request.quizCategory());
        imageQuiz.setS3Info(s3Info);

        imageQuizRepository.save(imageQuiz);
    }

    @Transactional
    public void updateImageQuiz(ImageQuizUpdateRequest request, Long quizId, MultipartFile multipartFile) {
        ImageQuiz imageQuiz = imageQuizRepository.findById(quizId)
                .orElseThrow(() -> new ImageQuizNotFoundException(IMAGE_QUIZ_NOT_FOUND));

        // 이미지 수정이 되지 않는 것 default
        S3Info s3Info = imageQuiz.getS3Info();

        if (!multipartFile.isEmpty()) {
            s3Uploader.deleteFile(imageQuiz.getS3Info());
            s3Info = uploadS3Info(multipartFile, request.quizCategory());
        }

        imageQuiz.updateImageQuiz(request.title(), request.answer(), request.quizCategory(), s3Info);
    }

    @Transactional
    public void deleteImageQuiz(Long quizId) {
        ImageQuiz imageQuiz = imageQuizRepository.findById(quizId)
                .orElseThrow(() -> new ImageQuizNotFoundException(IMAGE_QUIZ_NOT_FOUND));

        s3Uploader.deleteFile(imageQuiz.getS3Info());
        imageQuizRepository.delete(imageQuiz);
    }

    private S3Info uploadS3Info(MultipartFile file, QuizCategory quizCategory) {
        String folderName = QUIZ_BASE_FOLDER + quizCategory;
        return s3Uploader.upload(file, folderName);
    }

    private Page<ImageQuiz> generateRandomPageWithCategory(Member member, int quizIdx) {
        return imageQuizRepository.findSinglePageByMember(member, PageRequest.of(quizIdx, 1));
    }

    private int getImageQuizCount(Member member) {
        return imageQuizRepository.countByMemberOrAdmin(member);
    }

    private ImageQuiz getImageQuizFromPage(Page<ImageQuiz> imageQuizPage) {
        if (imageQuizPage.hasContent()) {
            return imageQuizPage.getContent().get(0);
        } else {
            throw new ImageQuizNotFoundException(IMAGE_QUIZ_NOT_FOUND);
        }
    }
}
