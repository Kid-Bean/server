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
                    .isDefault(false)
                    .build();

            WordQuiz WordQuiz2 = WordQuiz.builder()
                    .quizCategory(QuizCategory.ANIMAL)
                    .title("동물")
                    .answer("자")
                    .level(GOLD)
                    .member(DUMMY_MEMBER)
                    .isDefault(false)
                    .build();

            WordQuiz WordQuiz3 = WordQuiz.builder()
                    .quizCategory(QuizCategory.PLANT)
                    .title("식물")
                    .answer("물병")
                    .level(GOLD)
                    .member(DUMMY_MEMBER)
                    .isDefault(false)
                    .build();

            WordQuiz WordQuiz4 = WordQuiz.builder()
                    .quizCategory(QuizCategory.FOOD)
                    .title("음식")
                    .answer("비행기")
                    .level(SILVER)
                    .member(DUMMY_MEMBER)
                    .isDefault(false)
                    .build();

            WordQuiz WordQuiz5 = WordQuiz.builder()
                    .quizCategory(QuizCategory.PLANT)
                    .title("과일")
                    .answer("시계")
                    .level(DIAMOND)
                    .member(DUMMY_MEMBER)
                    .isDefault(false)
                    .build();

            WordQuiz WordQuiz6 = WordQuiz.builder()
                    .quizCategory(QuizCategory.FOOD)
                    .title("음식")
                    .answer("물고기")
                    .level(BRONZE)
                    .member(DUMMY_MEMBER)
                    .isDefault(false)
                    .build();

            WordQuiz WordQuiz7 = WordQuiz.builder()
                    .quizCategory(QuizCategory.NONE)
                    .title("신체")
                    .answer("코끼리")
                    .level(SILVER)
                    .member(DUMMY_MEMBER)
                    .isDefault(false)
                    .build();

            WordQuiz WordQuiz8 = WordQuiz.builder()
                    .quizCategory(QuizCategory.NONE)
                    .title("인물")
                    .answer("학교")
                    .level(PLATINUM)
                    .member(DUMMY_MEMBER)
                    .isDefault(false)
                    .build();

            WordQuiz WordQuiz9 = WordQuiz.builder()
                    .quizCategory(QuizCategory.ANIMAL)
                    .title("동물")
                    .answer("기차")
                    .level(SILVER)
                    .member(DUMMY_MEMBER)
                    .isDefault(false)
                    .build();

            WordQuiz WordQuiz10 = WordQuiz.builder()
                    .quizCategory(QuizCategory.NONE)
                    .title("날씨")
                    .answer("우산")
                    .level(DIAMOND)
                    .member(DUMMY_MEMBER)
                    .isDefault(false)
                    .build();

            WordQuiz WordQuiz11 = WordQuiz.builder()
                    .quizCategory(QuizCategory.OBJECT)
                    .title("운송수단")
                    .answer("고양이")
                    .level(DIAMOND)
                    .member(DUMMY_ADMIN)
                    .isDefault(true)
                    .build();

            WordQuiz WordQuiz12 = WordQuiz.builder()
                    .quizCategory(QuizCategory.NONE)
                    .title("모양")
                    .answer("신발")
                    .level(GOLD)
                    .member(DUMMY_ADMIN)
                    .isDefault(true)
                    .build();

            WordQuiz WordQuiz13 = WordQuiz.builder()
                    .quizCategory(QuizCategory.NONE)
                    .title("우주")
                    .answer("동그라미")
                    .level(PLATINUM)
                    .member(DUMMY_ADMIN)
                    .isDefault(true)
                    .build();

            WordQuiz WordQuiz14 = WordQuiz.builder()
                    .quizCategory(QuizCategory.ANIMAL)
                    .title("동물")
                    .answer("사과")
                    .level(BRONZE)
                    .member(DUMMY_ADMIN)
                    .isDefault(true)
                    .build();

            WordQuiz WordQuiz15 = WordQuiz.builder()
                    .quizCategory(QuizCategory.OBJECT)
                    .title("사물")
                    .answer("손")
                    .level(GOLD)
                    .member(DUMMY_ADMIN)
                    .isDefault(true)
                    .build();

            WordQuiz WordQuiz16 = WordQuiz.builder()
                    .quizCategory(QuizCategory.OBJECT)
                    .title("기계")
                    .answer("엄마")
                    .level(DIAMOND)
                    .member(DUMMY_ADMIN)
                    .isDefault(true)
                    .build();

            WordQuiz WordQuiz17 = WordQuiz.builder()
                    .quizCategory(QuizCategory.NONE)
                    .title("모양")
                    .answer("태양")
                    .level(DIAMOND)
                    .member(DUMMY_ADMIN)
                    .isDefault(true)
                    .build();

            WordQuiz WordQuiz18 = WordQuiz.builder()
                    .quizCategory(QuizCategory.OBJECT)
                    .title("기계")
                    .answer("다람쥐")
                    .level(SILVER)
                    .member(DUMMY_ADMIN)
                    .isDefault(true)
                    .build();

            WordQuiz WordQuiz19 = WordQuiz.builder()
                    .quizCategory(QuizCategory.NONE)
                    .title("가족")
                    .answer("보석")
                    .level(PLATINUM)
                    .member(DUMMY_ADMIN)
                    .isDefault(true)
                    .build();

            WordQuiz WordQuiz20 = WordQuiz.builder()
                    .quizCategory(QuizCategory.NONE)
                    .title("시간")
                    .answer("아빠")
                    .level(DIAMOND)
                    .member(DUMMY_ADMIN)
                    .isDefault(true)
                    .build();

            WordQuiz WordQuiz21 = WordQuiz.builder()
                    .quizCategory(QuizCategory.NONE)
                    .title("교통수단")
                    .answer("버스")
                    .level(SILVER)
                    .member(DUMMY_MEMBER)
                    .isDefault(false)
                    .build();

            WordQuiz WordQuiz22 = WordQuiz.builder()
                    .quizCategory(QuizCategory.ANIMAL)
                    .title("동물")
                    .answer("고양이")
                    .level(GOLD)
                    .member(DUMMY_MEMBER)
                    .isDefault(false)
                    .build();

            WordQuiz WordQuiz23 = WordQuiz.builder()
                    .quizCategory(QuizCategory.PLANT)
                    .title("식물")
                    .answer("나무")
                    .level(GOLD)
                    .member(DUMMY_MEMBER)
                    .isDefault(false)
                    .build();

            WordQuiz WordQuiz24 = WordQuiz.builder()
                    .quizCategory(QuizCategory.FOOD)
                    .title("과일")
                    .answer("딸기")
                    .level(SILVER)
                    .member(DUMMY_MEMBER)
                    .isDefault(false)
                    .build();

            WordQuiz WordQuiz25 = WordQuiz.builder()
                    .quizCategory(QuizCategory.NONE)
                    .title("가족")
                    .answer("형")
                    .level(DIAMOND)
                    .member(DUMMY_MEMBER)
                    .isDefault(false)
                    .build();

            WordQuiz WordQuiz26 = WordQuiz.builder()
                    .quizCategory(QuizCategory.FOOD)
                    .title("음식")
                    .answer("김밥")
                    .level(BRONZE)
                    .member(DUMMY_MEMBER)
                    .isDefault(false)
                    .build();

            WordQuiz WordQuiz27 = WordQuiz.builder()
                    .quizCategory(QuizCategory.NONE)
                    .title("신체")
                    .answer("눈")
                    .level(SILVER)
                    .member(DUMMY_MEMBER)
                    .isDefault(false)
                    .build();

            WordQuiz WordQuiz28 = WordQuiz.builder()
                    .quizCategory(QuizCategory.NONE)
                    .title("인물")
                    .answer("영화배우")
                    .level(PLATINUM)
                    .member(DUMMY_MEMBER)
                    .isDefault(false)
                    .build();

            WordQuiz WordQuiz29 = WordQuiz.builder()
                    .quizCategory(QuizCategory.ANIMAL)
                    .title("동물")
                    .answer("코끼리")
                    .level(SILVER)
                    .member(DUMMY_MEMBER)
                    .isDefault(false)
                    .build();

            WordQuiz WordQuiz30 = WordQuiz.builder()
                    .quizCategory(QuizCategory.NONE)
                    .title("날씨")
                    .answer("비")
                    .level(DIAMOND)
                    .member(DUMMY_MEMBER)
                    .isDefault(false)
                    .build();

            WordQuiz WordQuiz31 = WordQuiz.builder()
                    .quizCategory(QuizCategory.OBJECT)
                    .title("음악기기")
                    .answer("피아노")
                    .level(DIAMOND)
                    .member(DUMMY_ADMIN)
                    .isDefault(true)
                    .build();

            WordQuiz WordQuiz32 = WordQuiz.builder()
                    .quizCategory(QuizCategory.NONE)
                    .title("모양")
                    .answer("사각형")
                    .level(GOLD)
                    .member(DUMMY_ADMIN)
                    .isDefault(true)
                    .build();

            WordQuiz WordQuiz33 = WordQuiz.builder()
                    .quizCategory(QuizCategory.NONE)
                    .title("우주")
                    .answer("별자리")
                    .level(PLATINUM)
                    .member(DUMMY_ADMIN)
                    .isDefault(true)
                    .build();

            WordQuiz WordQuiz34 = WordQuiz.builder()
                    .quizCategory(QuizCategory.ANIMAL)
                    .title("동물")
                    .answer("사자")
                    .level(BRONZE)
                    .member(DUMMY_ADMIN)
                    .isDefault(true)
                    .build();

            WordQuiz WordQuiz35 = WordQuiz.builder()
                    .quizCategory(QuizCategory.OBJECT)
                    .title("사물")
                    .answer("책")
                    .level(GOLD)
                    .member(DUMMY_ADMIN)
                    .isDefault(true)
                    .build();

            WordQuiz WordQuiz36 = WordQuiz.builder()
                    .quizCategory(QuizCategory.OBJECT)
                    .title("기계")
                    .answer("전화기")
                    .level(DIAMOND)
                    .member(DUMMY_ADMIN)
                    .isDefault(true)
                    .build();

            WordQuiz WordQuiz37 = WordQuiz.builder()
                    .quizCategory(QuizCategory.NONE)
                    .title("모양")
                    .answer("원")
                    .level(DIAMOND)
                    .member(DUMMY_ADMIN)
                    .isDefault(true)
                    .build();

            WordQuiz WordQuiz38 = WordQuiz.builder()
                    .quizCategory(QuizCategory.OBJECT)
                    .title("기계")
                    .answer("냉장고")
                    .level(SILVER)
                    .member(DUMMY_ADMIN)
                    .isDefault(true)
                    .build();

            WordQuiz WordQuiz39 = WordQuiz.builder()
                    .quizCategory(QuizCategory.NONE)
                    .title("가족")
                    .answer("삼촌")
                    .level(PLATINUM)
                    .member(DUMMY_ADMIN)
                    .isDefault(true)
                    .build();

            WordQuiz WordQuiz40 = WordQuiz.builder()
                    .quizCategory(QuizCategory.NONE)
                    .title("시간")
                    .answer("분")
                    .level(DIAMOND)
                    .member(DUMMY_ADMIN)
                    .isDefault(true)
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
            WordQuizList.add(WordQuiz21);
            WordQuizList.add(WordQuiz22);
            WordQuizList.add(WordQuiz23);
            WordQuizList.add(WordQuiz24);
            WordQuizList.add(WordQuiz25);
            WordQuizList.add(WordQuiz26);
            WordQuizList.add(WordQuiz27);
            WordQuizList.add(WordQuiz28);
            WordQuizList.add(WordQuiz29);
            WordQuizList.add(WordQuiz30);
            WordQuizList.add(WordQuiz31);
            WordQuizList.add(WordQuiz32);
            WordQuizList.add(WordQuiz33);
            WordQuizList.add(WordQuiz34);
            WordQuizList.add(WordQuiz35);
            WordQuizList.add(WordQuiz36);
            WordQuizList.add(WordQuiz37);
            WordQuizList.add(WordQuiz38);
            WordQuizList.add(WordQuiz39);
            WordQuizList.add(WordQuiz40);

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

            wordList.add(new Word("버스", WordQuiz21));
            wordList.add(new Word("지하철", WordQuiz21));
            wordList.add(new Word("택시", WordQuiz21));
            wordList.add(new Word("자전거", WordQuiz21));

            wordList.add(new Word("고양이", WordQuiz22));
            wordList.add(new Word("강아지", WordQuiz22));
            wordList.add(new Word("말", WordQuiz22));
            wordList.add(new Word("토끼", WordQuiz22));

            wordList.add(new Word("나무", WordQuiz23));
            wordList.add(new Word("꽃", WordQuiz23));
            wordList.add(new Word("풀", WordQuiz23));
            wordList.add(new Word("선인장", WordQuiz23));

            wordList.add(new Word("딸기", WordQuiz24));
            wordList.add(new Word("바나나", WordQuiz24));
            wordList.add(new Word("사과", WordQuiz24));
            wordList.add(new Word("포도", WordQuiz24));

            wordList.add(new Word("형", WordQuiz25));
            wordList.add(new Word("동생", WordQuiz25));
            wordList.add(new Word("아빠", WordQuiz25));
            wordList.add(new Word("엄마", WordQuiz25));

            wordList.add(new Word("김밥", WordQuiz26));
            wordList.add(new Word("떡볶이", WordQuiz26));
            wordList.add(new Word("초밥", WordQuiz26));
            wordList.add(new Word("라면", WordQuiz26));

            wordList.add(new Word("눈", WordQuiz27));
            wordList.add(new Word("귀", WordQuiz27));
            wordList.add(new Word("입", WordQuiz27));
            wordList.add(new Word("손", WordQuiz27));

            wordList.add(new Word("영화배우", WordQuiz28));
            wordList.add(new Word("가수", WordQuiz28));
            wordList.add(new Word("작가", WordQuiz28));
            wordList.add(new Word("스포츠선수", WordQuiz28));

            wordList.add(new Word("코끼리", WordQuiz29));
            wordList.add(new Word("호랑이", WordQuiz29));
            wordList.add(new Word("사슴", WordQuiz29));
            wordList.add(new Word("개구리", WordQuiz29));

            wordList.add(new Word("비", WordQuiz30));
            wordList.add(new Word("눈", WordQuiz30));
            wordList.add(new Word("안개", WordQuiz30));
            wordList.add(new Word("바람", WordQuiz30));

            wordList.add(new Word("피아노", WordQuiz31));
            wordList.add(new Word("기타", WordQuiz31));
            wordList.add(new Word("드럼", WordQuiz31));
            wordList.add(new Word("바이올린", WordQuiz31));

            wordList.add(new Word("사각형", WordQuiz32));
            wordList.add(new Word("삼각형", WordQuiz32));
            wordList.add(new Word("원", WordQuiz32));
            wordList.add(new Word("다각형", WordQuiz32));

            wordList.add(new Word("별자리", WordQuiz33));
            wordList.add(new Word("행성", WordQuiz33));
            wordList.add(new Word("우주선", WordQuiz33));
            wordList.add(new Word("은하", WordQuiz33));

            wordList.add(new Word("사자", WordQuiz34));
            wordList.add(new Word("호랑이", WordQuiz34));
            wordList.add(new Word("곰", WordQuiz34));
            wordList.add(new Word("늑대", WordQuiz34));

            wordList.add(new Word("책", WordQuiz35));
            wordList.add(new Word("연필", WordQuiz35));
            wordList.add(new Word("종이", WordQuiz35));
            wordList.add(new Word("노트", WordQuiz35));

            wordList.add(new Word("전화기", WordQuiz36));
            wordList.add(new Word("컴퓨터", WordQuiz36));
            wordList.add(new Word("텔레비전", WordQuiz36));
            wordList.add(new Word("프린터", WordQuiz36));

            wordList.add(new Word("원", WordQuiz37));
            wordList.add(new Word("정사각형", WordQuiz37));
            wordList.add(new Word("타원", WordQuiz37));
            wordList.add(new Word("삼각형", WordQuiz37));

            wordList.add(new Word("냉장고", WordQuiz38));
            wordList.add(new Word("세탁기", WordQuiz38));
            wordList.add(new Word("에어컨", WordQuiz38));
            wordList.add(new Word("전자레인지", WordQuiz38));

            wordList.add(new Word("삼촌", WordQuiz39));
            wordList.add(new Word("이모", WordQuiz39));
            wordList.add(new Word("조부모", WordQuiz39));
            wordList.add(new Word("사촌", WordQuiz39));

            wordList.add(new Word("분", WordQuiz40));
            wordList.add(new Word("초", WordQuiz40));
            wordList.add(new Word("시간", WordQuiz40));
            wordList.add(new Word("날", WordQuiz40));

            wordQuizRepository.saveAll(WordQuizList);
            wordRepository.saveAll(wordList);
        }
    }
}
