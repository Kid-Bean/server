package soongsil.kidbean.server.quiz.application;

import ch.qos.logback.core.testUtil.RandomUtil;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import soongsil.kidbean.server.global.application.S3Uploader;
import soongsil.kidbean.server.global.vo.ImageInfo;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.domain.type.Role;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.mypage.dto.response.SolvedRecordInfo;
import soongsil.kidbean.server.mypage.dto.response.SolvedRecordListResponse;
import soongsil.kidbean.server.quiz.domain.ImageQuiz;
import soongsil.kidbean.server.quiz.domain.type.Category;
import soongsil.kidbean.server.quiz.dto.request.ImageQuizSolvedRequest;
import soongsil.kidbean.server.quiz.dto.request.ImageQuizUpdateRequest;
import soongsil.kidbean.server.quiz.dto.request.ImageQuizUploadRequest;
import soongsil.kidbean.server.quiz.dto.response.ImageQuizMemberDetailResponse;
import soongsil.kidbean.server.quiz.dto.response.ImageQuizMemberResponse;
import soongsil.kidbean.server.quiz.dto.response.ImageQuizResponse;
import soongsil.kidbean.server.quiz.exception.ImageQuizNotFoundException;
import soongsil.kidbean.server.quiz.exception.MemberNotFoundException;
import soongsil.kidbean.server.quiz.exception.MemberNotMatchException;
import soongsil.kidbean.server.quiz.repository.ImageQuizRepository;

import java.io.IOException;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageQuizService {

    private final ImageQuizRepository imageQuizRepository;
    private final MemberRepository memberRepository;
    private final ImageQuizSolvedService imageQuizSolvedService;
    private final S3Uploader s3Uploader;

    private static final String COMMON_URL = "kidbean.s3.ap-northeast-2.amazonaws.com";
    private static final String QUIZ_NAME = "quiz/";

    public ImageQuizMemberDetailResponse getImageQuizById(Long memberId, Long quizId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(RuntimeException::new);
        ImageQuiz imageQuiz = imageQuizRepository.findByQuizIdAndMember(quizId, member)
                .orElseThrow(RuntimeException::new);

        return ImageQuizMemberDetailResponse.from(imageQuiz);
    }

    public List<ImageQuizMemberResponse> getAllImageQuizByMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        return imageQuizRepository.findAllByMember(member)
                .stream()
                .map(ImageQuizMemberResponse::from)
                .toList();
    }

    /**
     * @param imageQuizSolvedRequestList DTO의 List
     * @param memberId                   문제를 푼 유저의 id
     * @return 추가된 점수
     */
    @Transactional
    public Long solveImageQuizzes(
            List<ImageQuizSolvedRequest> imageQuizSolvedRequestList,
            Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        return imageQuizSolvedService.solveImageQuizzes(imageQuizSolvedRequestList, member);
    }

    /**
     * 해당 카테고리에서 문제를 랜덤으로 선택
     *
     * @param memberId 문제를 풀 멤버의 id
     * @return 이미지 퀴즈 DTO
     */
    public ImageQuizResponse selectRandomProblem(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        Category category = selectRandomCategory();
        Page<ImageQuiz> imageQuizPage = generateRandomPageWithCategory(member, category);

        ImageQuiz imageQuiz = pageHasImageQuiz(imageQuizPage)
                .orElseThrow(ImageQuizNotFoundException::new);

        return ImageQuizResponse.from(imageQuiz);
    }

    /**
     * 랜덤 카테고리 선택
     *
     * @return 랜덤 카테고리
     */
    private Category selectRandomCategory() {
        return Category.valueOfCode(RandomUtil.getPositiveInt() % 4);
    }

    /**
     * 랜덤 ImageQuiz를 Page로 감싸서 return
     *
     * @param member   멤버
     * @param category 카테고리
     * @return 풀 이미지 퀴즈가 있는 Page
     */
    private Page<ImageQuiz> generateRandomPageWithCategory(Member member, Category category) {
        int divVal = getImageQuizCount(member, category);
        int idx = RandomUtil.getPositiveInt() % divVal;

        log.info("divVal: {}, idx: {}", divVal, idx);

        return imageQuizRepository.findAllImageQuizWithPage(
                member, Role.ADMIN, category, PageRequest.of(idx, 1));
    }

    /**
     * 이미지 퀴즈 총 개수(admin + member가 올린 전체)
     *
     * @param member   멤버
     * @param category 카테고리
     * @return 해당 멤버와 관리자가 해당 카테고리에 등록한 이미지 퀴즈의 총 수
     */
    private int getImageQuizCount(Member member, Category category) {
        int userProblemCount = imageQuizRepository.countByMemberAndCategory(member, category);
        int adminProblemCount = imageQuizRepository.countByMember_RoleAndCategory(Role.ADMIN, category);

        log.info("userCnt: {}, adminCnt: {}", userProblemCount, adminProblemCount);

        return userProblemCount + adminProblemCount;
    }

    /**
     * page가 ImageQuiz를 가지고 있는지 확인 후 Optional로 return
     *
     * @param imageQuizPage 찾는 이미지 퀴즈가 있는 페이지
     * @return 이미지 퀴즈
     */
    private Optional<ImageQuiz> pageHasImageQuiz(Page<ImageQuiz> imageQuizPage) {
        if (imageQuizPage.hasContent()) {
            return Optional.of(imageQuizPage.getContent().get(0));
        } else {
            return Optional.empty();
        }
    }
  
    @Transactional
    public void uploadImageQuiz(ImageQuizUploadRequest request, Long memberId, MultipartFile image) throws IOException {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        String folderName = QUIZ_NAME +  request.category();
        String uploadUrl = s3Uploader.upload(image, folderName);

        String generatedPath = uploadUrl.split("/" + COMMON_URL + "/" + folderName + "/")[1];

        ImageQuiz imageQuiz = request.toImageQuiz(member);
        imageQuiz.setImageInfo(
                ImageInfo.builder()
                        .imageUrl(uploadUrl)
                        .fileName(generatedPath)
                        .folderName(QUIZ_NAME + request.category())
                        .build());

        imageQuizRepository.save(imageQuiz);
    }

    @Transactional
    public void updateImageQuiz(ImageQuizUpdateRequest request, Long memberId, Long quizId, MultipartFile image) throws IOException {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        ImageQuiz imageQuiz = imageQuizRepository.findById(quizId)
                .orElseThrow(ImageQuizNotFoundException::new);

        // 이미지 수정이 되지 않는 것 default
        ImageInfo imageInfo = imageQuiz.getImageInfo();

        if (!image.getOriginalFilename().isEmpty()) {
            s3Uploader.deleteFile(imageQuiz.getImageInfo());

            String updateFolderName = QUIZ_NAME + request.category();
            String updateUrl = s3Uploader.upload(image, updateFolderName);
            String generatedPath = updateUrl.split("/" + COMMON_URL + "/" + updateFolderName + "/")[1];
            imageInfo = ImageInfo.builder()
                    .imageUrl(updateUrl)
                    .fileName(generatedPath)
                    .folderName(QUIZ_NAME + request.category())
                    .build();
        }

        imageQuiz.update(request.title(), request.answer(), request.category(), imageInfo);
    }

    @Transactional
    public void deleteImageQuiz(Long memberId, Long quizId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        ImageQuiz imageQuiz = imageQuizRepository.findById(quizId)
                .orElseThrow(ImageQuizNotFoundException::new);

        imageQuizRepository.delete(imageQuiz);
        imageQuizRepository.flush();
    }
}
