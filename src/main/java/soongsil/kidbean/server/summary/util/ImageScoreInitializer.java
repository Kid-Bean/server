package soongsil.kidbean.server.summary.util;

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
import soongsil.kidbean.server.quiz.application.vo.QuizType;
import soongsil.kidbean.server.quiz.domain.type.QuizCategory;
import soongsil.kidbean.server.summary.domain.QuizScore;
import soongsil.kidbean.server.summary.repository.QuizScoreRepository;

@Slf4j
@RequiredArgsConstructor
@Order(4)
@LocalDummyDataInit
public class ImageScoreInitializer implements ApplicationRunner {

    private final QuizScoreRepository quizScoreRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (quizScoreRepository.count() > 0) {
            log.info("[ImageQuizScore]더미 데이터 존재");
        } else {
            List<QuizScore> imageQuizScores = new ArrayList<>();
            imageQuizScores.add(makeImageQuizScore(100L, QuizCategory.ANIMAL, 12L, DUMMY_MEMBER));
            imageQuizScores.add(makeImageQuizScore(120L, QuizCategory.PLANT, 12L, DUMMY_MEMBER));
            imageQuizScores.add(makeImageQuizScore(130L, QuizCategory.OBJECT, 13L, DUMMY_MEMBER));
            imageQuizScores.add(makeImageQuizScore(150L, QuizCategory.NONE, 14L, DUMMY_MEMBER));

            List<QuizScore> wordQuizScores = new ArrayList<>();
            wordQuizScores.add(makeWordQuizScore(100L, QuizCategory.ANIMAL, 12L, DUMMY_MEMBER));
            wordQuizScores.add(makeWordQuizScore(120L, QuizCategory.PLANT, 12L, DUMMY_MEMBER));
            wordQuizScores.add(makeWordQuizScore(130L, QuizCategory.OBJECT, 13L, DUMMY_MEMBER));
            wordQuizScores.add(makeWordQuizScore(150L, QuizCategory.NONE, 14L, DUMMY_MEMBER));

            quizScoreRepository.saveAll(imageQuizScores);
            quizScoreRepository.saveAll(wordQuizScores);
        }
    }

    private QuizScore makeWordQuizScore(Long totalScore, QuizCategory quizCategory, Long quizCount, Member member) {
        return QuizScore.builder()
                .member(DUMMY_MEMBER)
                .totalScore(totalScore)
                .quizCategory(quizCategory)
                .quizType(QuizType.WORD_QUIZ)
                .quizCount(quizCount)
                .build();
    }

    private QuizScore makeImageQuizScore(Long totalScore, QuizCategory quizCategory, Long quizCount, Member member) {
        return QuizScore.builder()
                .member(DUMMY_MEMBER)
                .totalScore(totalScore)
                .quizCategory(quizCategory)
                .quizType(QuizType.IMAGE_QUIZ)
                .quizCount(quizCount)
                .build();
    }
}
