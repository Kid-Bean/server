package soongsil.kidbean.server.quiz.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import soongsil.kidbean.server.global.util.LocalDummyDataInit;
import soongsil.kidbean.server.quiz.domain.AnswerQuiz;
import soongsil.kidbean.server.quiz.repository.AnswerQuizRepository;

import java.util.ArrayList;
import java.util.List;

import static soongsil.kidbean.server.member.util.MemberInitializer.DUMMY_ADMIN;
import static soongsil.kidbean.server.member.util.MemberInitializer.DUMMY_MEMBER;

@Slf4j
@RequiredArgsConstructor
@Order(2)
@LocalDummyDataInit
public class AnswerQuizInitializer implements ApplicationRunner {

    private final AnswerQuizRepository answerQuizRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (answerQuizRepository.count() > 0) {
            log.info("[AnswerQuiz]더미 데이터 존재");
        } else {
            List<AnswerQuiz> answerQuizList = new ArrayList<>();

            AnswerQuiz answerQuiz1 = AnswerQuiz.builder()
                    .question("오늘 점심 뭐 먹었어?")
                    .member(DUMMY_ADMIN)
                    .build();
            answerQuizList.add(answerQuiz1);

            AnswerQuiz answerQuiz2 = AnswerQuiz.builder()
                    .question("엄마가 좋아 아빠가 좋아?")
                    .member(DUMMY_ADMIN)
                    .build();
            answerQuizList.add(answerQuiz2);

            AnswerQuiz answerQuiz3 = AnswerQuiz.builder()
                    .question("좋아하는 장난감 있어?")
                    .member(DUMMY_MEMBER)
                    .build();
            answerQuizList.add(answerQuiz3);

            AnswerQuiz answerQuiz4 = AnswerQuiz.builder()
                    .question("어제 재밌는 일 있었어?")
                    .member(DUMMY_MEMBER)
                    .build();
            answerQuizList.add(answerQuiz4);

            answerQuizRepository.saveAll(answerQuizList);
        }
    }
}
