package soongsil.kidbean.server.wordquiz.repository.init;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import soongsil.kidbean.server.global.util.LocalDummyDataInit;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.wordquiz.domain.Word;
import soongsil.kidbean.server.wordquiz.domain.WordQuiz;
import soongsil.kidbean.server.quizsolve.domain.type.QuizCategory;
import soongsil.kidbean.server.wordquiz.repository.WordQuizRepository;
import soongsil.kidbean.server.wordquiz.repository.WordRepository;

import java.util.ArrayList;
import java.util.List;

import static soongsil.kidbean.server.quizsolve.domain.type.Level.*;

@Slf4j
@RequiredArgsConstructor
@Order(3)
@LocalDummyDataInit
public class WordQuizInitializer implements ApplicationRunner {

    private final MemberRepository memberRepository;
    private final WordQuizRepository wordQuizRepository;
    private final WordRepository wordRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (wordQuizRepository.count() > 0 || wordRepository.count() > 0) {
            log.info("[WordQuiz]더미 데이터 존재");
        } else {
            Member DUMMY_MEMBER = memberRepository.findById(1L).orElseThrow();
            Member DUMMY_ADMIN = memberRepository.findById(2L).orElseThrow();

            List<WordQuiz> WordQuizList = new ArrayList<>();

            WordQuiz WordQuiz1 = WordQuiz.builder()
                    .quizCategory(QuizCategory.NONE)
                    .title("가족")
                    .answer("자동차")
                    .level(SILVER)
                    .member(DUMMY_MEMBER)
                    .build();

            WordQuiz WordQuiz2 = WordQuiz.builder()
                    .quizCategory(QuizCategory.ANIMAL)
                    .title("동물")
                    .answer("자")
                    .level(GOLD)
                    .member(DUMMY_MEMBER)
                    .build();

            WordQuiz WordQuiz3 = WordQuiz.builder()
                    .quizCategory(QuizCategory.PLANT)
                    .title("식물")
                    .answer("물병")
                    .level(GOLD)
                    .member(DUMMY_MEMBER)
                    .build();

            WordQuiz WordQuiz4 = WordQuiz.builder()
                    .quizCategory(QuizCategory.FOOD)
                    .title("음식")
                    .answer("비행기")
                    .level(SILVER)
                    .member(DUMMY_MEMBER)
                    .build();

            WordQuiz WordQuiz5 = WordQuiz.builder()
                    .quizCategory(QuizCategory.PLANT)
                    .title("과일")
                    .answer("시계")
                    .level(DIAMOND)
                    .member(DUMMY_MEMBER)
                    .build();

            WordQuiz WordQuiz6 = WordQuiz.builder()
                    .quizCategory(QuizCategory.FOOD)
                    .title("음식")
                    .answer("물고기")
                    .level(BRONZE)
                    .member(DUMMY_MEMBER)
                    .build();

            WordQuiz WordQuiz7 = WordQuiz.builder()
                    .quizCategory(QuizCategory.NONE)
                    .title("신체")
                    .answer("코끼리")
                    .level(SILVER)
                    .member(DUMMY_MEMBER)
                    .build();

            WordQuiz WordQuiz8 = WordQuiz.builder()
                    .quizCategory(QuizCategory.NONE)
                    .title("인물")
                    .answer("학교")
                    .level(PLATINUM)
                    .member(DUMMY_MEMBER)
                    .build();

            WordQuiz WordQuiz9 = WordQuiz.builder()
                    .quizCategory(QuizCategory.ANIMAL)
                    .title("동물")
                    .answer("기차")
                    .level(SILVER)
                    .member(DUMMY_MEMBER)
                    .build();

            WordQuiz WordQuiz10 = WordQuiz.builder()
                    .quizCategory(QuizCategory.NONE)
                    .title("날씨")
                    .answer("우산")
                    .level(DIAMOND)
                    .member(DUMMY_MEMBER)
                    .build();

            WordQuiz WordQuiz11 = WordQuiz.builder()
                    .quizCategory(QuizCategory.OBJECT)
                    .title("운송수단")
                    .answer("고양이")
                    .level(DIAMOND)
                    .member(DUMMY_ADMIN)
                    .build();

            WordQuiz WordQuiz12 = WordQuiz.builder()
                    .quizCategory(QuizCategory.NONE)
                    .title("모양")
                    .answer("신발")
                    .level(GOLD)
                    .member(DUMMY_ADMIN)
                    .build();

            WordQuiz WordQuiz13 = WordQuiz.builder()
                    .quizCategory(QuizCategory.NONE)
                    .title("우주")
                    .answer("동그라미")
                    .level(PLATINUM)
                    .member(DUMMY_ADMIN)
                    .build();

            WordQuiz WordQuiz14 = WordQuiz.builder()
                    .quizCategory(QuizCategory.ANIMAL)
                    .title("동물")
                    .answer("사과")
                    .level(BRONZE)
                    .member(DUMMY_ADMIN)
                    .build();

            WordQuiz WordQuiz15 = WordQuiz.builder()
                    .quizCategory(QuizCategory.OBJECT)
                    .title("사물")
                    .answer("손")
                    .level(GOLD)
                    .member(DUMMY_ADMIN)
                    .build();

            WordQuiz WordQuiz16 = WordQuiz.builder()
                    .quizCategory(QuizCategory.OBJECT)
                    .title("기계")
                    .answer("엄마")
                    .level(DIAMOND)
                    .member(DUMMY_ADMIN)
                    .build();

            WordQuiz WordQuiz17 = WordQuiz.builder()
                    .quizCategory(QuizCategory.NONE)
                    .title("모양")
                    .answer("태양")
                    .level(DIAMOND)
                    .member(DUMMY_ADMIN)
                    .build();

            WordQuiz WordQuiz18 = WordQuiz.builder()
                    .quizCategory(QuizCategory.OBJECT)
                    .title("기계")
                    .answer("다람쥐")
                    .level(SILVER)
                    .member(DUMMY_ADMIN)
                    .build();

            WordQuiz WordQuiz19 = WordQuiz.builder()
                    .quizCategory(QuizCategory.NONE)
                    .title("가족")
                    .answer("보석")
                    .level(PLATINUM)
                    .member(DUMMY_ADMIN)
                    .build();

            WordQuiz WordQuiz20 = WordQuiz.builder()
                    .quizCategory(QuizCategory.NONE)
                    .title("시간")
                    .answer("아빠")
                    .level(DIAMOND)
                    .member(DUMMY_ADMIN)
                    .build();

            WordQuizList.add(WordQuiz1);
            WordQuizList.add(WordQuiz2);
            WordQuizList.add(WordQuiz3);
            WordQuizList.add(WordQuiz4);
            WordQuizList.add(WordQuiz5);
            WordQuizList.add(WordQuiz6);
            WordQuizList.add(WordQuiz7);
            WordQuizList.add(WordQuiz8);
            WordQuizList.add(WordQuiz9);
            WordQuizList.add(WordQuiz10);
            WordQuizList.add(WordQuiz11);
            WordQuizList.add(WordQuiz12);
            WordQuizList.add(WordQuiz13);
            WordQuizList.add(WordQuiz14);
            WordQuizList.add(WordQuiz15);
            WordQuizList.add(WordQuiz16);
            WordQuizList.add(WordQuiz17);
            WordQuizList.add(WordQuiz18);
            WordQuizList.add(WordQuiz19);
            WordQuizList.add(WordQuiz20);

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

            wordList.add(new Word("비행기", WordQuiz4));
            wordList.add(new Word("호박", WordQuiz4));
            wordList.add(new Word("시금치", WordQuiz4));
            wordList.add(new Word("당근", WordQuiz4));

            wordList.add(new Word("수박", WordQuiz5));
            wordList.add(new Word("시계", WordQuiz5));
            wordList.add(new Word("토마토", WordQuiz5));
            wordList.add(new Word("참외", WordQuiz5));

            wordList.add(new Word("물고기", WordQuiz6));
            wordList.add(new Word("소고기", WordQuiz6));
            wordList.add(new Word("물김치", WordQuiz6));
            wordList.add(new Word("비빔밥", WordQuiz6));

            wordList.add(new Word("코", WordQuiz7));
            wordList.add(new Word("손", WordQuiz7));
            wordList.add(new Word("발가락", WordQuiz7));
            wordList.add(new Word("코끼리", WordQuiz7));

            wordList.add(new Word("엄마", WordQuiz8));
            wordList.add(new Word("선생님", WordQuiz8));
            wordList.add(new Word("학교", WordQuiz8));
            wordList.add(new Word("아기", WordQuiz8));

            wordList.add(new Word("하마", WordQuiz9));
            wordList.add(new Word("호랑이", WordQuiz9));
            wordList.add(new Word("표범", WordQuiz9));
            wordList.add(new Word("기차", WordQuiz9));

            wordList.add(new Word("우산", WordQuiz10));
            wordList.add(new Word("구름", WordQuiz10));
            wordList.add(new Word("바람", WordQuiz10));
            wordList.add(new Word("태풍", WordQuiz10));

            wordList.add(new Word("비행기", WordQuiz11));
            wordList.add(new Word("기차", WordQuiz11));
            wordList.add(new Word("고양이", WordQuiz11));
            wordList.add(new Word("고속버스", WordQuiz11));

            wordList.add(new Word("동그라미", WordQuiz12));
            wordList.add(new Word("신발", WordQuiz12));
            wordList.add(new Word("네모", WordQuiz12));
            wordList.add(new Word("세모", WordQuiz12));

            wordList.add(new Word("동그라미", WordQuiz13));
            wordList.add(new Word("달", WordQuiz13));
            wordList.add(new Word("해", WordQuiz13));
            wordList.add(new Word("별", WordQuiz13));

            wordList.add(new Word("사과", WordQuiz14));
            wordList.add(new Word("사슴", WordQuiz14));
            wordList.add(new Word("토끼", WordQuiz14));
            wordList.add(new Word("얼룩말", WordQuiz14));

            wordList.add(new Word("안경", WordQuiz15));
            wordList.add(new Word("의자", WordQuiz15));
            wordList.add(new Word("손", WordQuiz15));
            wordList.add(new Word("옷", WordQuiz15));

            wordList.add(new Word("선풍기", WordQuiz16));
            wordList.add(new Word("다리미", WordQuiz16));
            wordList.add(new Word("컴퓨터", WordQuiz16));
            wordList.add(new Word("엄마", WordQuiz16));

            wordList.add(new Word("태양", WordQuiz17));
            wordList.add(new Word("네모", WordQuiz17));
            wordList.add(new Word("세모", WordQuiz17));
            wordList.add(new Word("동그라미", WordQuiz17));

            wordList.add(new Word("다람쥐", WordQuiz18));
            wordList.add(new Word("다리미", WordQuiz18));
            wordList.add(new Word("세탁기", WordQuiz18));
            wordList.add(new Word("건조기", WordQuiz18));

            wordList.add(new Word("할아버지", WordQuiz19));
            wordList.add(new Word("동생", WordQuiz19));
            wordList.add(new Word("보석", WordQuiz19));
            wordList.add(new Word("할머니", WordQuiz19));

            wordList.add(new Word("아빠", WordQuiz20));
            wordList.add(new Word("아침", WordQuiz20));
            wordList.add(new Word("점심", WordQuiz20));
            wordList.add(new Word("저녁", WordQuiz20));

            wordQuizRepository.saveAll(WordQuizList);
            wordRepository.saveAll(wordList);
        }
    }
}
