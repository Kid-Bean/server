package soongsil.kidbean.server.member.util;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.domain.type.Gender;
import soongsil.kidbean.server.member.domain.type.Role;
import soongsil.kidbean.server.member.repository.MemberRepository;

@Slf4j
@RequiredArgsConstructor
@Profile("dev")
@Component
public class MemberInitializer implements ApplicationRunner {

    private final MemberRepository memberRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (memberRepository.count() >= 5) {
            log.info("[Member]더미 데이터 존재");
        } else {
            List<Member> memberList = new ArrayList<>();
            memberList.add(Member.builder()
                    .email("email1")
                    .name("name1")
                    .gender(Gender.MAN)
                    .role(Role.USER)
                    .score(25L)
                    .build());
            memberList.add(Member.builder()
                    .email("email2")
                    .name("name2")
                    .gender(Gender.WOMAN)
                    .role(Role.USER)
                    .score(2L)
                    .build());
            memberList.add(Member.builder()
                    .email("email3")
                    .name("name3")
                    .gender(Gender.MAN)
                    .role(Role.USER)
                    .score(6L)
                    .build());
            memberList.add(Member.builder()
                    .email("email4")
                    .name("name4")
                    .gender(Gender.MAN)
                    .role(Role.USER)
                    .score(12L)
                    .build());
            memberList.add(Member.builder()
                    .email("email5")
                    .name("name5")
                    .gender(Gender.MAN)
                    .role(Role.ADMIN)
                    .score(3L)
                    .build());

            memberRepository.saveAll(memberList);
        }
    }
}
