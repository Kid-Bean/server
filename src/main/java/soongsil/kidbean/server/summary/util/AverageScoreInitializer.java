package soongsil.kidbean.server.summary.util;

import static soongsil.kidbean.server.member.util.MemberInitializer.DUMMY_ADMIN;
import static soongsil.kidbean.server.member.util.MemberInitializer.DUMMY_MEMBER;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import soongsil.kidbean.server.global.util.LocalDummyDataInit;
import soongsil.kidbean.server.quiz.domain.AnswerQuiz;
import soongsil.kidbean.server.quiz.domain.type.QuizCategory;
import soongsil.kidbean.server.quiz.repository.AnswerQuizRepository;
import soongsil.kidbean.server.summary.domain.AverageScore;
import soongsil.kidbean.server.summary.domain.type.AgeGroup;
import soongsil.kidbean.server.summary.repository.AverageScoreRepository;

@Slf4j
@RequiredArgsConstructor
@Order(4)
@LocalDummyDataInit
public class AverageScoreInitializer implements ApplicationRunner {

    private final AverageScoreRepository averageScoreRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (averageScoreRepository.count() > 0) {
            log.info("[AverageScore]더미 데이터 존재");
        } else {
            List<AverageScore> averageScores = new ArrayList<>();

            averageScores.add(makeAverageScore(100, 1600L, AgeGroup.BEFORE_ONE, QuizCategory.ANIMAL));
            averageScores.add(makeAverageScore(205, 100030L, AgeGroup.BEFORE_ONE, QuizCategory.PLANT));
            averageScores.add(makeAverageScore(2000, 10L, AgeGroup.BEFORE_ONE, QuizCategory.OBJECT));
            averageScores.add(makeAverageScore(10000, 10010L, AgeGroup.BEFORE_ONE, QuizCategory.NONE));

            averageScoreRepository.saveAll(averageScores);
        }
    }

    private AverageScore makeAverageScore(long quizCount, Long sum, AgeGroup ageGroup, QuizCategory quizCategory) {
        return AverageScore.builder()
                .memberCount(quizCount)
                .sum(sum)
                .ageGroup(ageGroup)
                .category(quizCategory)
                .build();
    }
}
