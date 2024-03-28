package soongsil.kidbean.server.quiz.application;

import static soongsil.kidbean.server.quiz.util.ImageQuizCache.imageQuizUserProblemCountMap;

import ch.qos.logback.core.testUtil.RandomUtil;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.member.domain.Member;
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
        int divVal = getImageQuizCountForMemberAndCategory(member, category);
        int idx = RandomUtil.getPositiveInt() % divVal;

        //해당 부분도 캐시로 하려 했으나 문제가 많아질 수도 있고 map의 depth가 깊어짐
        //추후 리팩토링 시 쿼리가 더 많이 나가지 않게 고민 필요
        //또한 page를 사용해서 쿼리가 한번에 2번 나감 -> ImageQuiz 전체 select 한번, count 한번
        return imageQuizRepository.findAllByMemberAndCategory(member, category, PageRequest.of(idx, 1));
    }

    // 이미지 퀴즈 총 개수를 가져오는 메서드
    private int getImageQuizCountForMemberAndCategory(Member member, Category category) {
        Long memberId = member.getMemberId();

        // 캐시에서 이미 존재하는 경우 캐시에서 값을 가져옴
        if (imageQuizUserProblemCountMap.get(category).containsKey(memberId)) {
            log.info("category: {}, memberId: {}", category, memberId);
            return imageQuizUserProblemCountMap.get(category).get(memberId);
        } else {
            // 캐시에 존재하지 않는 경우 데이터베이스에서 조회하여 캐시에 저장
            int problemCount = imageQuizRepository.countByMemberAndCategory(member, category);
            imageQuizUserProblemCountMap.get(category).put(memberId, problemCount);
            return problemCount;
        }
    }

    private Optional<ImageQuiz> pageHasImageQuiz(Page<ImageQuiz> imageQuizPage) {
        if (imageQuizPage.hasContent()) {
            return Optional.of(imageQuizPage.getContent().get(0));
        } else {
            return Optional.empty();
        }
    }
}
