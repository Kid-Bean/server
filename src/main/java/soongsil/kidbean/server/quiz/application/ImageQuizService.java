package soongsil.kidbean.server.quiz.application;

import ch.qos.logback.core.testUtil.RandomUtil;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.domain.type.Role;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.quiz.domain.ImageQuiz;
import soongsil.kidbean.server.quiz.domain.type.Category;
import soongsil.kidbean.server.quiz.dto.response.ImageQuizMemberDetailResponse;
import soongsil.kidbean.server.quiz.dto.response.ImageQuizResponse;
import soongsil.kidbean.server.quiz.exception.ImageQuizNotFoundException;
import soongsil.kidbean.server.quiz.exception.MemberNotFoundException;
import soongsil.kidbean.server.quiz.repository.ImageQuizRepository;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageQuizService {

    private final ImageQuizRepository imageQuizRepository;
    private final MemberRepository memberRepository;

    public ImageQuizMemberDetailResponse getImageQuizById(Long memberId, Long quizId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(RuntimeException::new);
        ImageQuiz imageQuiz = imageQuizRepository.findByMemberAndQuizId(member, quizId)
                .orElseThrow(RuntimeException::new);

        return ImageQuizMemberDetailResponse.from(imageQuiz);
    }

    //해당 카테고리에서 문제를 랜덤으로 선택
    public ImageQuizResponse selectRandomProblem(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(MemberNotFoundException::new);

        Category category = selectRandomCategory();
        Page<ImageQuiz> imageQuizPage = generateRandomPageWithCategory(member, category);

        ImageQuiz imageQuiz = pageHasImageQuiz(imageQuizPage)
                .orElseThrow(ImageQuizNotFoundException::new);

        return ImageQuizResponse.from(imageQuiz);
    }

    private Category selectRandomCategory() {
        return Category.valueOfCode(RandomUtil.getPositiveInt() % 4);
    }

    private Page<ImageQuiz> generateRandomPageWithCategory(Member member, Category category) {
        int divVal = getImageQuizCount(member, category);
        int idx = RandomUtil.getPositiveInt() % divVal;

        log.info("divVal: {}, idx: {}", divVal, idx);

        return imageQuizRepository.findAllImageQuizWithPage(
                member, Role.ADMIN, category, PageRequest.of(idx, 1));
    }

    // 이미지 퀴즈 총 개수(admin + member가 올린 전체)
    private int getImageQuizCount(Member member, Category category) {
        int userProblemCount = imageQuizRepository.countByMemberAndCategory(member, category);
        int adminProblemCount = imageQuizRepository.countByMember_RoleAndCategory(Role.ADMIN, category);

        log.info("userCnt: {}, adminCnt: {}", userProblemCount, adminProblemCount);

        return userProblemCount + adminProblemCount;
    }

    private Optional<ImageQuiz> pageHasImageQuiz(Page<ImageQuiz> imageQuizPage) {
        if (imageQuizPage.hasContent()) {
            return Optional.of(imageQuizPage.getContent().get(0));
        } else {
            return Optional.empty();
        }
    }
}
