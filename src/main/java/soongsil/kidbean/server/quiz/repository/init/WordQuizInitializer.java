package soongsil.kidbean.server.quiz.repository.init;

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

import static soongsil.kidbean.server.member.repository.init.MemberInitializer.DUMMY_ADMIN;
import static soongsil.kidbean.server.member.repository.init.MemberInitializer.DUMMY_MEMBER;
import static soongsil.kidbean.server.quiz.domain.type.Level.DIAMOND;
import static soongsil.kidbean.server.quiz.domain.type.Level.GOLD;
import static soongsil.kidbean.server.quiz.domain.type.Level.SILVER;

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
                    .title("가족")
                    .answer("자동차")
                    .level(SILVER)
                    .member(DUMMY_MEMBER)
                    .build();
            WordQuizList.add(WordQuiz1);

            WordQuiz WordQuiz2 = WordQuiz.builder()
                    .title("동물")
                    .answer("자")
                    .level(GOLD)
                    .member(DUMMY_ADMIN)
                    .build();
            WordQuizList.add(WordQuiz2);

            WordQuiz WordQuiz3 = WordQuiz.builder()
                    .title("식물")
                    .answer("물병")
                    .level(DIAMOND)
                    .member(DUMMY_ADMIN)
                    .build();
            WordQuizList.add(WordQuiz3);

            List<Word> wordList = new ArrayList<>();
            wordList.add(new Word("엄마", WordQuiz1));
            wordList.add(new Word("아빠", WordQuiz1));
            wordList.add(new Word("할아버지", WordQuiz1));
            wordList.add(new Word("자동차", WordQuiz1));
            wordList.add(new Word("자", WordQuiz2));
            wordList.add(new Word("고래", WordQuiz2));
            wordList.add(new Word("낙타", WordQuiz2));
            wordList.add(new Word("강아지", WordQuiz2));
            wordList.add(new Word("물병", WordQuiz3));
            wordList.add(new Word("선인장", WordQuiz3));
            wordList.add(new Word("진달래", WordQuiz3));
            wordList.add(new Word("소나무", WordQuiz3));

            wordQuizRepository.saveAll(WordQuizList);
            wordRepository.saveAll(wordList);
        }
    }
}
