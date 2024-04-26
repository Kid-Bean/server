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
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quiz.domain.AnswerQuiz;
import soongsil.kidbean.server.quiz.domain.type.QuizCategory;
import soongsil.kidbean.server.quiz.repository.AnswerQuizRepository;
import soongsil.kidbean.server.summary.domain.AverageScore;
import soongsil.kidbean.server.summary.domain.ImageQuizScore;
import soongsil.kidbean.server.summary.domain.type.AgeGroup;
import soongsil.kidbean.server.summary.repository.ImageQuizScoreRepository;

@Slf4j
@RequiredArgsConstructor
@Order(4)
@LocalDummyDataInit
public class ImageScoreInitializer implements ApplicationRunner {

    private final ImageQuizScoreRepository imageQuizScoreRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (imageQuizScoreRepository.count() > 0) {
            log.info("[ImageQuizScore]더미 데이터 존재");
        } else {
            List<ImageQuizScore> imageQuizScores = new ArrayList<>();
            imageQuizScores.add(makeImageQuizScore(100L, QuizCategory.ANIMAL, 12L, DUMMY_MEMBER));
            imageQuizScores.add(makeImageQuizScore(120L, QuizCategory.PLANT, 12L, DUMMY_MEMBER));
            imageQuizScores.add(makeImageQuizScore(130L, QuizCategory.OBJECT, 13L, DUMMY_MEMBER));
            imageQuizScores.add(makeImageQuizScore(150L, QuizCategory.NONE, 14L, DUMMY_MEMBER));

            imageQuizScoreRepository.saveAll(imageQuizScores);
        }
    }

    private ImageQuizScore makeImageQuizScore(Long totalScore, QuizCategory quizCategory, Long quizCount, Member member) {
        return ImageQuizScore.builder()
                .member(DUMMY_MEMBER)
                .totalScore(10L)
                .quizCategory(quizCategory)
                .quizCount(quizCount)
                .build();
    }
}
