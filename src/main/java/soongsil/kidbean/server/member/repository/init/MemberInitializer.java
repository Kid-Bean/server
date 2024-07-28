package soongsil.kidbean.server.member.repository.init;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import soongsil.kidbean.server.global.util.LocalDummyDataInit;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.domain.type.Gender;
import soongsil.kidbean.server.member.domain.type.Role;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.quizsolve.domain.type.QuizCategory;
import soongsil.kidbean.server.summary.domain.QuizScore;
import soongsil.kidbean.server.summary.repository.QuizScoreRepository;

@Slf4j
@RequiredArgsConstructor
@Order(1)
@LocalDummyDataInit
public class MemberInitializer implements ApplicationRunner {

    private final MemberRepository memberRepository;
    private final QuizScoreRepository quizScoreRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (memberRepository.count() > 0) {
            log.info("[Member]더미 데이터 존재");
        } else {
            List<Member> memberList = new ArrayList<>();

            for (int i = 0; i < 1; i++) {
                Member DUMMY_MEMBER = Member.builder()
                        .email("email1")
                        .name("name1")
                        .socialId("socialId1")
                        .gender(Gender.MAN)
                        .role(Role.MEMBER)
                        .score(25L)
                        .build();
                Member DUMMY_ADMIN = Member.builder()
                        .email("email5")
                        .name("adminName")
                        .socialId("socialId2")
                        .gender(Gender.MAN)
                        .role(Role.ADMIN)
                        .score(3L)
                        .build();

                memberList.add(DUMMY_MEMBER);
                memberList.add(DUMMY_ADMIN);
                memberList.add(Member.builder()
                        .email("email2")
                        .name("name2")
                        .gender(Gender.WOMAN)
                        .socialId("socialId3")
                        .role(Role.MEMBER)
                        .score(2L)
                        .build());
                memberList.add(Member.builder()
                        .email("email3")
                        .name("name3")
                        .gender(Gender.MAN)
                        .role(Role.MEMBER)
                        .socialId("socialId4")
                        .score(6L)
                        .build());
                memberList.add(Member.builder()
                        .email("email4")
                        .name("name4")
                        .gender(Gender.MAN)
                        .role(Role.GUEST)
                        .socialId("socialId5")
                        .score(12L)
                        .build());
            }

            List<Member> members = memberRepository.saveAll(memberList);

            members.forEach(
                    member -> QuizCategory.allValue()
                            .forEach(quizCategory ->
                                    quizScoreRepository.save(QuizScore.makeInitQuizScore(member, quizCategory)))
            );
        }
    }
}
