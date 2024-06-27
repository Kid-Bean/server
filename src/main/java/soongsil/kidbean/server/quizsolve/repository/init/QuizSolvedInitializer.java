package soongsil.kidbean.server.quizsolve.repository.init;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import soongsil.kidbean.server.global.util.LocalDummyDataInit;
import soongsil.kidbean.server.quizsolve.domain.QuizSolved;
import soongsil.kidbean.server.quizsolve.repository.QuizSolvedRepository;

import java.util.ArrayList;
import java.util.List;

import static soongsil.kidbean.server.member.repository.init.MemberInitializer.DUMMY_MEMBER;
import static soongsil.kidbean.server.imagequiz.repository.init.ImageQuizInitializer.*;

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
                    .reply("사과")
                    .build());

            quizSolvedList.add(QuizSolved.builder()
                    .imageQuiz(IMAGE_QUIZ_FOOD1)
                    .isCorrect(false)
                    .member(DUMMY_MEMBER)
                    .reply("배")
                    .build());

            quizSolvedList.add(QuizSolved.builder()
                    .imageQuiz(IMAGE_QUIZ_ANIMAL3)
                    .isCorrect(false)
                    .member(DUMMY_MEMBER)
                    .reply("귤")
                    .build());

            quizSolvedList.add(QuizSolved.builder()
                    .imageQuiz(IMAGE_QUIZ_ANIMAL3)
                    .isCorrect(false)
                    .member(DUMMY_MEMBER)
                    .reply("포도")
                    .build());

            quizSolvedRepository.saveAll(quizSolvedList);
        }
    }
}
