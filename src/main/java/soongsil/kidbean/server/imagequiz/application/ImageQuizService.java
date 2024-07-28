package soongsil.kidbean.server.imagequiz.application;

import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import soongsil.kidbean.server.global.application.S3Uploader;
import soongsil.kidbean.server.global.domain.S3Info;
import soongsil.kidbean.server.imagequiz.util.ImageQuizCountCache;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.exception.MemberNotFoundException;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.quizsolve.application.QuizSolvedService;
import soongsil.kidbean.server.imagequiz.domain.ImageQuiz;
import soongsil.kidbean.server.quizsolve.domain.type.QuizCategory;
import soongsil.kidbean.server.quizsolve.dto.request.QuizSolvedRequest;
import soongsil.kidbean.server.imagequiz.dto.request.ImageQuizUpdateRequest;
import soongsil.kidbean.server.imagequiz.dto.request.ImageQuizUploadRequest;
import soongsil.kidbean.server.imagequiz.dto.response.ImageQuizMemberDetailResponse;
import soongsil.kidbean.server.imagequiz.dto.response.ImageQuizMemberResponse;
import soongsil.kidbean.server.imagequiz.dto.response.ImageQuizSolveListResponse;
import soongsil.kidbean.server.imagequiz.dto.response.ImageQuizSolveResponse;
import soongsil.kidbean.server.imagequiz.dto.response.ImageQuizSolveScoreResponse;
import soongsil.kidbean.server.imagequiz.exception.ImageQuizNotFoundException;
import soongsil.kidbean.server.imagequiz.repository.ImageQuizRepository;

import java.util.List;

import static soongsil.kidbean.server.imagequiz.exception.errorcode.ImageQuizErrorCode.IMAGE_QUIZ_NOT_FOUND;
import static soongsil.kidbean.server.member.exception.errorcode.MemberErrorCode.MEMBER_NOT_FOUND;
import static soongsil.kidbean.server.quizsolve.application.vo.QuizType.IMAGE_QUIZ;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageQuizService {

    private final ImageQuizRepository imageQuizRepository;
    private final MemberRepository memberRepository;
    private final QuizSolvedService quizSolvedService;
    private final S3Uploader s3Uploader;

    private final ImageQuizCountCache imageQuizCountCache;

    private static final String QUIZ_BASE_FOLDER = "quiz/";

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
    public ImageQuizSolveScoreResponse solveImageQuizzes(
            List<QuizSolvedRequest> quizSolvedRequestList, Long memberId
    ) {
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

        long count = imageQuizCountCache.get(memberId);
        PageRequest pageRequest = makeRandomPageRequest(count, quizNum);

        List<ImageQuizSolveResponse> imageQuizSolveResponseList =
                imageQuizRepository.findRandomQuizzesByMember(memberId, pageRequest);

        return ImageQuizSolveListResponse.from(imageQuizSolveResponseList);
    }

    private PageRequest makeRandomPageRequest(long count, int quizNum) {
        return PageRequest.of(ThreadLocalRandom.current().nextInt((int) (count / quizNum)), quizNum);
    }

    @Transactional
    public void uploadImageQuiz(ImageQuizUploadRequest request, Long memberId, MultipartFile multipartFile) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));

        ImageQuiz imageQuiz = request.toImageQuiz(member);
        S3Info s3Info = uploadS3Info(multipartFile, request.quizCategory());
        imageQuiz.setS3Info(s3Info);

        imageQuizRepository.save(imageQuiz);

        imageQuizCountCache.plusCount(memberId);
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
    public void deleteImageQuiz(Long quizId, Long memberId) {
        ImageQuiz imageQuiz = imageQuizRepository.findById(quizId)
                .orElseThrow(() -> new ImageQuizNotFoundException(IMAGE_QUIZ_NOT_FOUND));

        s3Uploader.deleteFile(imageQuiz.getS3Info());
        imageQuizRepository.delete(imageQuiz);

        imageQuizCountCache.minusCount(memberId);
    }

    private S3Info uploadS3Info(MultipartFile multipartFile, QuizCategory quizCategory) {
        String folderName = QUIZ_BASE_FOLDER + quizCategory;
        return s3Uploader.upload(multipartFile, folderName);
    }
}
