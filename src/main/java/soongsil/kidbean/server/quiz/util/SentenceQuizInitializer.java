package soongsil.kidbean.server.quiz.util;

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
import soongsil.kidbean.server.quiz.domain.SentenceQuiz;
import soongsil.kidbean.server.quiz.domain.SentenceQuizWord;
import soongsil.kidbean.server.quiz.repository.SentenceQuizRepository;
import soongsil.kidbean.server.quiz.repository.SentenceQuizWordRepository;

@Slf4j
@RequiredArgsConstructor
@Order(2)
@LocalDummyDataInit
public class SentenceQuizInitializer implements ApplicationRunner {

    private final SentenceQuizRepository sentenceQuizRepository;
    private final SentenceQuizWordRepository sentenceQuizWordRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (sentenceQuizRepository.count() > 0 || sentenceQuizWordRepository.count() > 0) {
            log.info("[SentenceQuiz]더미 데이터 존재");
        } else {
            List<SentenceQuiz> sentenceQuizList = new ArrayList<>();

            SentenceQuiz sentenceQuiz1 = SentenceQuiz.builder()
                    .title("sentenceQuiz1")
                    .member(DUMMY_MEMBER)
                    .words(List.of(new SentenceQuizWord("word1", null),
                            new SentenceQuizWord("word2", null),
                            new SentenceQuizWord("word3", null)))
                    .build();
            sentenceQuizList.add(sentenceQuiz1);

            SentenceQuiz sentenceQuiz2 = SentenceQuiz.builder()
                    .title("sentenceQuiz2")
                    .member(DUMMY_ADMIN)
                    .words(List.of(new SentenceQuizWord("word1", null),
                            new SentenceQuizWord("word2", null),
                            new SentenceQuizWord("word3", null)))
                    .build();
            sentenceQuizList.add(sentenceQuiz2);

            SentenceQuiz sentenceQuiz3 = SentenceQuiz.builder()
                    .title("sentenceQuiz3")
                    .member(DUMMY_ADMIN)
                    .words(List.of(new SentenceQuizWord("word1", null),
                            new SentenceQuizWord("word2", null)))
                    .build();
            sentenceQuizList.add(sentenceQuiz3);

            List<SentenceQuizWord> sentenceQuizWordList = new ArrayList<>();
            sentenceQuizWordList.add(new SentenceQuizWord("word1", sentenceQuiz1));
            sentenceQuizWordList.add(new SentenceQuizWord("word2", sentenceQuiz1));
            sentenceQuizWordList.add(new SentenceQuizWord("word3", sentenceQuiz1));
            sentenceQuizWordList.add(new SentenceQuizWord("word1", sentenceQuiz2));
            sentenceQuizWordList.add(new SentenceQuizWord("word2", sentenceQuiz2));
            sentenceQuizWordList.add(new SentenceQuizWord("word3", sentenceQuiz2));
            sentenceQuizWordList.add(new SentenceQuizWord("word1", sentenceQuiz3));
            sentenceQuizWordList.add(new SentenceQuizWord("word2", sentenceQuiz3));

            sentenceQuizRepository.saveAll(sentenceQuizList);
            sentenceQuizWordRepository.saveAll(sentenceQuizWordList);
        }
    }
}
