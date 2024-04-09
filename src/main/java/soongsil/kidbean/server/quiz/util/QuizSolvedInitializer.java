package soongsil.kidbean.server.quiz.util;

import static soongsil.kidbean.server.member.util.MemberInitializer.DUMMY_MEMBER;
import static soongsil.kidbean.server.quiz.util.ImageQuizInitializer.IMAGE_QUIZ_1;
import static soongsil.kidbean.server.quiz.util.ImageQuizInitializer.IMAGE_QUIZ_2;
import static soongsil.kidbean.server.quiz.util.ImageQuizInitializer.IMAGE_QUIZ_3;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import soongsil.kidbean.server.global.util.LocalDummyDataInit;
import soongsil.kidbean.server.quiz.domain.QuizSolved;
import soongsil.kidbean.server.quiz.repository.QuizSolvedRepository;

@Slf4j
@RequiredArgsConstructor
@Order(3)
@LocalDummyDataInit
public class QuizSolvedInitializer implements ApplicationRunner {

    private final QuizSolvedRepository quizSolvedRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (quizSolvedRepository.count() > 0) {
            log.info("[QuizSolved]더미 데이터 존재");
        } else {
            //더미 데이터 작성
            log.info("[QuizSolved]더미 데이터 작성");
            List<QuizSolved> quizSolvedList = new ArrayList<>();

            quizSolvedList.add(QuizSolved.builder()
                    .imageQuiz(IMAGE_QUIZ_1)
                    .isCorrect(false)
                    .member(DUMMY_MEMBER)
                    .solvedTime(LocalDateTime.now())
                    .reply("사과")
                    .build());

            quizSolvedList.add(QuizSolved.builder()
                    .imageQuiz(IMAGE_QUIZ_2)
                    .isCorrect(false)
                    .member(DUMMY_MEMBER)
                    .solvedTime(LocalDateTime.now())
                    .reply("배")
                    .build());

            quizSolvedList.add(QuizSolved.builder()
                    .imageQuiz(IMAGE_QUIZ_2)
                    .isCorrect(false)
                    .member(DUMMY_MEMBER)
                    .solvedTime(LocalDateTime.now())
                    .reply("귤")
                    .build());

            quizSolvedList.add(QuizSolved.builder()
                    .imageQuiz(IMAGE_QUIZ_3)
                    .isCorrect(false)
                    .member(DUMMY_MEMBER)
                    .solvedTime(LocalDateTime.now())
                    .reply("포도")
                    .build());

            quizSolvedRepository.saveAll(quizSolvedList);
        }
    }
}
