package soongsil.kidbean.server.quiz.repository.init;

import static soongsil.kidbean.server.member.repository.init.MemberInitializer.DUMMY_MEMBER;
import static soongsil.kidbean.server.quiz.repository.init.ImageQuizInitializer.IMAGE_QUIZ_ANIMAL1;
import static soongsil.kidbean.server.quiz.repository.init.ImageQuizInitializer.IMAGE_QUIZ_ANIMAL3;
import static soongsil.kidbean.server.quiz.repository.init.ImageQuizInitializer.IMAGE_QUIZ_FOOD1;

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
                    .imageQuiz(IMAGE_QUIZ_ANIMAL1)
                    .isCorrect(false)
                    .member(DUMMY_MEMBER)
                    .solvedTime(LocalDateTime.now())
                    .reply("사과")
                    .build());

            quizSolvedList.add(QuizSolved.builder()
                    .imageQuiz(IMAGE_QUIZ_FOOD1)
                    .isCorrect(false)
                    .member(DUMMY_MEMBER)
                    .solvedTime(LocalDateTime.now())
                    .reply("배")
                    .build());

            quizSolvedList.add(QuizSolved.builder()
                    .imageQuiz(IMAGE_QUIZ_ANIMAL3)
                    .isCorrect(false)
                    .member(DUMMY_MEMBER)
                    .solvedTime(LocalDateTime.now())
                    .reply("귤")
                    .build());

            quizSolvedList.add(QuizSolved.builder()
                    .imageQuiz(IMAGE_QUIZ_ANIMAL3)
                    .isCorrect(false)
                    .member(DUMMY_MEMBER)
                    .solvedTime(LocalDateTime.now())
                    .reply("포도")
                    .build());

            quizSolvedRepository.saveAll(quizSolvedList);
        }
    }
}