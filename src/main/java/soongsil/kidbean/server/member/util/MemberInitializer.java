package soongsil.kidbean.server.member.util;

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

@Slf4j
@RequiredArgsConstructor
@Order(1)
@LocalDummyDataInit
public class MemberInitializer implements ApplicationRunner {

    private final MemberRepository memberRepository;

    public static final Member DUMMY_MEMBER = Member.builder()
            .email("email1")
            .name("name1")
            .gender(Gender.MAN)
            .role(Role.MEMBER)
            .score(25L)
            .build();
    public static final Member DUMMY_ADMIN = Member.builder()
            .email("email5")
            .name("name5")
            .gender(Gender.MAN)
            .role(Role.ADMIN)
            .score(3L)
            .build();

    @Override
    public void run(ApplicationArguments args) {
        if (memberRepository.count() > 0) {
            log.info("[Member]더미 데이터 존재");
        } else {
            List<Member> memberList = new ArrayList<>();

            memberList.add(DUMMY_MEMBER);
            memberList.add(DUMMY_ADMIN);
            memberList.add(Member.builder()
                    .email("email2")
                    .name("name2")
                    .gender(Gender.WOMAN)
                    .role(Role.MEMBER)
                    .score(2L)
                    .build());
            memberList.add(Member.builder()
                    .email("email3")
                    .name("name3")
                    .gender(Gender.MAN)
                    .role(Role.MEMBER)
                    .score(6L)
                    .build());
            memberList.add(Member.builder()
                    .email("email4")
                    .name("name4")
                    .gender(Gender.MAN)
                    .role(Role.MEMBER)
                    .score(12L)
                    .build());

            memberRepository.saveAll(memberList);
        }
    }
}
