package soongsil.kidbean.server.answerquiz.repository.init;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import soongsil.kidbean.server.global.util.LocalDummyDataInit;
import soongsil.kidbean.server.answerquiz.domain.AnswerQuiz;
import soongsil.kidbean.server.answerquiz.repository.AnswerQuizRepository;

import java.util.ArrayList;
import java.util.List;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.repository.MemberRepository;

@Slf4j
@RequiredArgsConstructor
@Order(2)
@LocalDummyDataInit
public class AnswerQuizInitializer implements ApplicationRunner {

    private final MemberRepository memberRepository;
    private final AnswerQuizRepository answerQuizRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (answerQuizRepository.count() > 0) {
            log.info("[AnswerQuiz]더미 데이터 존재");
        } else {
            Member DUMMY_MEMBER = memberRepository.findBySocialId("socialId1").orElseThrow();
            Member DUMMY_ADMIN = memberRepository.findBySocialId("socialId2").orElseThrow();

            List<AnswerQuiz> answerQuizList = new ArrayList<>();

            AnswerQuiz answerQuiz1 = AnswerQuiz.builder()
                    .title("meal")
                    .question("오늘 점심 뭐 먹었어?")
                    .member(DUMMY_ADMIN)
                    .build();
            answerQuizList.add(answerQuiz1);

            AnswerQuiz answerQuiz2 = AnswerQuiz.builder()
                    .title("proper")
                    .question("엄마가 좋아 아빠가 좋아?")
                    .member(DUMMY_ADMIN)
                    .build();
            answerQuizList.add(answerQuiz2);

            AnswerQuiz answerQuiz3 = AnswerQuiz.builder()
                    .title("like")
                    .question("좋아하는 장난감 있어?")
                    .member(DUMMY_MEMBER)
                    .build();
            answerQuizList.add(answerQuiz3);

            AnswerQuiz answerQuiz4 = AnswerQuiz.builder()
                    .title("exciting")
                    .question("어제 재밌는 일 있었어?")
                    .member(DUMMY_MEMBER)
                    .build();
            answerQuizList.add(answerQuiz4);

            AnswerQuiz answerQuiz5 = AnswerQuiz.builder()
                    .title("가장 좋아하는 동물")
                    .question("가장 좋아하는 동물은 무엇이야?")
                    .member(DUMMY_ADMIN)
                    .build();
            answerQuizList.add(answerQuiz5);

            AnswerQuiz answerQuiz6 = AnswerQuiz.builder()
                    .title("하늘을 날 수 있다면?")
                    .question("하늘을 날 수 있다면, 어디로 가고 싶어?")
                    .member(DUMMY_ADMIN)
                    .build();
            answerQuizList.add(answerQuiz6);

            AnswerQuiz answerQuiz7 = AnswerQuiz.builder()
                    .title("가장 재미있는 책")
                    .question("가장 재미있는 책이 뭐야?")
                    .member(DUMMY_ADMIN)
                    .build();
            answerQuizList.add(answerQuiz7);

            AnswerQuiz answerQuiz8 = AnswerQuiz.builder()
                    .title("제일 먹고 싶은 음식")
                    .question("지금 제일 먹고 싶은 음식이 뭐야?")
                    .member(DUMMY_ADMIN)
                    .build();
            answerQuizList.add(answerQuiz8);

            AnswerQuiz answerQuiz9 = AnswerQuiz.builder()
                    .title("가장 친한 친구")
                    .question("가장 친한 친구가 누구야?")
                    .member(DUMMY_ADMIN)
                    .build();
            answerQuizList.add(answerQuiz9);

            AnswerQuiz answerQuiz10 = AnswerQuiz.builder()
                    .title("로봇이 된다면?")
                    .question("오늘 하루만이라도 로봇이 되어 본다면, 무엇을 해보고 싶어?")
                    .member(DUMMY_ADMIN)
                    .build();
            answerQuizList.add(answerQuiz10);

            AnswerQuiz answerQuiz11 = AnswerQuiz.builder()
                    .title("가장 좋아하는 색은?")
                    .question("가장 좋아하는 색은 무엇이야?")
                    .member(DUMMY_ADMIN)
                    .build();
            answerQuizList.add(answerQuiz11);

            AnswerQuiz answerQuiz12 = AnswerQuiz.builder()
                    .title("장래희망")
                    .question("커서 뭐가 되고 싶어?")
                    .member(DUMMY_ADMIN)
                    .build();
            answerQuizList.add(answerQuiz12);

            AnswerQuiz answerQuiz13 = AnswerQuiz.builder()
                    .title("되고 싶은 캐릭터")
                    .question("만약 동화 속에 나오는 캐릭터가 된다면, 누구로 변신했으면 좋겠어?")
                    .member(DUMMY_ADMIN)
                    .build();
            answerQuizList.add(answerQuiz13);

            AnswerQuiz answerQuiz14 = AnswerQuiz.builder()
                    .title("좋아하는 캐릭터")
                    .question("제일 좋아하는 캐릭터가 뭐야?")
                    .member(DUMMY_ADMIN)
                    .build();
            answerQuizList.add(answerQuiz14);

            AnswerQuiz answerQuiz15 = AnswerQuiz.builder()
                    .title("부모님이랑 가장 하고 싶은거")
                    .question("엄마, 아빠랑 가장 하고 싶은게 뭐야?")
                    .member(DUMMY_ADMIN)
                    .build();
            answerQuizList.add(answerQuiz15);

            AnswerQuiz answerQuiz16 = AnswerQuiz.builder()
                    .title("부모님이랑 가장 가고 싶은 곳")
                    .question("엄마, 아빠랑 가장 가고 싶은 곳이 어디야?")
                    .member(DUMMY_ADMIN)
                    .build();
            answerQuizList.add(answerQuiz16);

            AnswerQuiz answerQuiz17 = AnswerQuiz.builder()
                    .title("가장 좋아하는 사람")
                    .question("가장 좋아하는 사람이 누구야?")
                    .member(DUMMY_ADMIN)
                    .build();
            answerQuizList.add(answerQuiz17);

            AnswerQuiz answerQuiz18 = AnswerQuiz.builder()
                    .title("오늘 뭐하고 놀았어")
                    .question("오늘 뭐하고 놀았어?")
                    .member(DUMMY_ADMIN)
                    .build();
            answerQuizList.add(answerQuiz18);

            AnswerQuiz answerQuiz19 = AnswerQuiz.builder()
                    .title("내일 하고 싶은 것")
                    .question("내일 뭐 하고 싶어?")
                    .member(DUMMY_ADMIN)
                    .build();
            answerQuizList.add(answerQuiz19);

            AnswerQuiz answerQuiz20 = AnswerQuiz.builder()
                    .title("제일 재미없는 시간")
                    .question("언제 제일 재미없어?")
                    .member(DUMMY_ADMIN)
                    .build();
            answerQuizList.add(answerQuiz20);

            AnswerQuiz answerQuiz21 = AnswerQuiz.builder()
                    .title("제일 재미없는 것")
                    .question("무엇이 제일 재미없어?")
                    .member(DUMMY_ADMIN)
                    .build();
            answerQuizList.add(answerQuiz21);

            AnswerQuiz answerQuiz22 = AnswerQuiz.builder()
                    .title("제일 재미있는 놀이")
                    .question("뭐하고 놀 때 제일 재미있어?")
                    .member(DUMMY_ADMIN)
                    .build();
            answerQuizList.add(answerQuiz22);

            AnswerQuiz answerQuiz23 = AnswerQuiz.builder()
                    .title("제일 좋아하는 과일")
                    .question("제일 좋아하는 과일이 있어?")
                    .member(DUMMY_ADMIN)
                    .build();
            answerQuizList.add(answerQuiz23);

            AnswerQuiz answerQuiz24 = AnswerQuiz.builder()
                    .title("제일 좋아하는 옷")
                    .question("제일 좋아하는 옷이 뭐야? 왜 좋아해?")
                    .member(DUMMY_ADMIN)
                    .build();
            answerQuizList.add(answerQuiz24);

            AnswerQuiz answerQuiz25 = AnswerQuiz.builder()
                    .title("보고 싶은 사람")
                    .question("지금 제일 보고 싶은 사람이 누구야?")
                    .member(DUMMY_ADMIN)
                    .build();
            answerQuizList.add(answerQuiz25);

            answerQuizRepository.saveAll(answerQuizList);
        }
    }
}
