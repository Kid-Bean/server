package soongsil.kidbean.server.quiz.util;

import static soongsil.kidbean.server.member.util.MemberInitializer.DUMMY_ADMIN;
import static soongsil.kidbean.server.member.util.MemberInitializer.DUMMY_MEMBER;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import soongsil.kidbean.server.global.util.LocalDummyDataInit;
import soongsil.kidbean.server.quiz.domain.WordQuiz;
import soongsil.kidbean.server.quiz.domain.WordQuizWord;
import soongsil.kidbean.server.quiz.repository.WordQuizRepository;
import soongsil.kidbean.server.quiz.repository.WordQuizWordRepository;

@Slf4j
@RequiredArgsConstructor
@LocalDummyDataInit
public class WordQuizInitializer implements ApplicationRunner {

    private final WordQuizRepository wordQuizRepository;
    private final WordQuizWordRepository wordQuizWordRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (wordQuizRepository.count() > 0 || wordQuizWordRepository.count() > 0) {
            log.info("[WordQuiz]더미 데이터 존재");
        } else {
            List<WordQuiz> WordQuizList = new ArrayList<>();

            WordQuiz WordQuiz1 = WordQuiz.builder()
                    .title("WordQuiz1")
                    .member(DUMMY_MEMBER)
                    .words(List.of(new WordQuizWord("word1", null),
                            new WordQuizWord("word2", null),
                            new WordQuizWord("word3", null)))
                    .build();
            WordQuizList.add(WordQuiz1);

            WordQuiz WordQuiz2 = WordQuiz.builder()
                    .title("WordQuiz2")
                    .member(DUMMY_ADMIN)
                    .words(List.of(new WordQuizWord("word1", null),
                            new WordQuizWord("word2", null),
                            new WordQuizWord("word3", null)))
                    .build();
            WordQuizList.add(WordQuiz2);

            WordQuiz WordQuiz3 = WordQuiz.builder()
                    .title("WordQuiz3")
                    .member(DUMMY_ADMIN)
                    .words(List.of(new WordQuizWord("word1", null),
                            new WordQuizWord("word2", null)))
                    .build();
            WordQuizList.add(WordQuiz3);

            List<WordQuizWord> WordQuizWordList = new ArrayList<>();
            WordQuizWordList.add(new WordQuizWord("word1", WordQuiz1));
            WordQuizWordList.add(new WordQuizWord("word2", WordQuiz1));
            WordQuizWordList.add(new WordQuizWord("word3", WordQuiz1));
            WordQuizWordList.add(new WordQuizWord("word1", WordQuiz2));
            WordQuizWordList.add(new WordQuizWord("word2", WordQuiz2));
            WordQuizWordList.add(new WordQuizWord("word3", WordQuiz2));
            WordQuizWordList.add(new WordQuizWord("word1", WordQuiz3));
            WordQuizWordList.add(new WordQuizWord("word2", WordQuiz3));

            wordQuizRepository.saveAll(WordQuizList);
            wordQuizWordRepository.saveAll(WordQuizWordList);
        }
    }
}
