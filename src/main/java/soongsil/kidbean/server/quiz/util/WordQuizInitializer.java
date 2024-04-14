package soongsil.kidbean.server.quiz.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import soongsil.kidbean.server.global.util.LocalDummyDataInit;
import soongsil.kidbean.server.quiz.domain.Word;
import soongsil.kidbean.server.quiz.domain.WordQuiz;
import soongsil.kidbean.server.quiz.repository.WordQuizRepository;
import soongsil.kidbean.server.quiz.repository.WordRepository;

import java.util.ArrayList;
import java.util.List;

import static soongsil.kidbean.server.member.util.MemberInitializer.DUMMY_ADMIN;
import static soongsil.kidbean.server.member.util.MemberInitializer.DUMMY_MEMBER;
import static soongsil.kidbean.server.quiz.domain.type.Level.BRONZE;
import static soongsil.kidbean.server.quiz.domain.type.Level.DIAMOND;
import static soongsil.kidbean.server.quiz.domain.type.Level.PLATINUM;

@Slf4j
@RequiredArgsConstructor
@Order(2)
@LocalDummyDataInit
public class WordQuizInitializer implements ApplicationRunner {

    private final WordQuizRepository wordQuizRepository;
    private final WordRepository wordRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (wordQuizRepository.count() > 0 || wordRepository.count() > 0) {
            log.info("[WordQuiz]더미 데이터 존재");
        } else {
            List<WordQuiz> WordQuizList = new ArrayList<>();

            WordQuiz WordQuiz1 = WordQuiz.builder()
                    .title("WordQuiz1")
                    .answer("word1")
                    .member(DUMMY_MEMBER)
                    .build();
            WordQuizList.add(WordQuiz1);

            WordQuiz WordQuiz2 = WordQuiz.builder()
                    .title("WordQuiz2")
                    .answer("word3")
                    .member(DUMMY_ADMIN)
                    .build();
            WordQuizList.add(WordQuiz2);

            WordQuiz WordQuiz3 = WordQuiz.builder()
                    .title("WordQuiz3")
                    .answer("word4")
                    .level(DIAMOND)
                    .member(DUMMY_ADMIN)
                    .build();
            WordQuizList.add(WordQuiz3);

            List<Word> wordList = new ArrayList<>();
            wordList.add(new Word("word1", WordQuiz1));
            wordList.add(new Word("word2", WordQuiz1));
            wordList.add(new Word("word3", WordQuiz1));
            wordList.add(new Word("word4", WordQuiz1));
            wordList.add(new Word("word1", WordQuiz2));
            wordList.add(new Word("word2", WordQuiz2));
            wordList.add(new Word("word3", WordQuiz2));
            wordList.add(new Word("word4", WordQuiz2));
            wordList.add(new Word("word1", WordQuiz3));
            wordList.add(new Word("word2", WordQuiz3));
            wordList.add(new Word("word3", WordQuiz3));
            wordList.add(new Word("word4", WordQuiz3));

            wordQuizRepository.saveAll(WordQuizList);
            wordRepository.saveAll(wordList);
        }
    }
}
