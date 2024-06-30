package soongsil.kidbean.server.summary.repository.init;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import soongsil.kidbean.server.global.util.LocalDummyDataInit;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.quizsolve.domain.type.QuizCategory;
import soongsil.kidbean.server.summary.domain.QuizScore;
import soongsil.kidbean.server.summary.repository.QuizScoreRepository;

@Slf4j
@RequiredArgsConstructor
@Order(4)
@LocalDummyDataInit
public class ImageScoreInitializer implements ApplicationRunner {

    private final MemberRepository memberRepository;
    private final QuizScoreRepository quizScoreRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (quizScoreRepository.count() > 0) {
            log.info("[ImageQuizScore]더미 데이터 존재");
        } else {
            Member DUMMY_MEMBER = memberRepository.findById(1L).orElseThrow();

            List<QuizScore> imageQuizScores = new ArrayList<>();

            imageQuizScores.add(makeImageQuizScore(100L, QuizCategory.ANIMAL, 12L, DUMMY_MEMBER));
            imageQuizScores.add(makeImageQuizScore(120L, QuizCategory.PLANT, 12L, DUMMY_MEMBER));
            imageQuizScores.add(makeImageQuizScore(130L, QuizCategory.OBJECT, 13L, DUMMY_MEMBER));
            imageQuizScores.add(makeImageQuizScore(150L, QuizCategory.NONE, 14L, DUMMY_MEMBER));

            quizScoreRepository.saveAll(imageQuizScores);
        }
    }

    private QuizScore makeImageQuizScore(Long totalScore, QuizCategory quizCategory, Long quizCount, Member member) {
        return QuizScore.builder()
                .member(member)
                .totalScore(totalScore)
                .quizCategory(quizCategory)
                .quizCount(quizCount)
                .build();
    }
}
