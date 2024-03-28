package soongsil.kidbean.server.member.util;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.domain.type.Gender;
import soongsil.kidbean.server.member.repository.MemberRepository;

@Slf4j
@RequiredArgsConstructor
@Profile("dev")
@Component
public class MemberInitializer implements ApplicationRunner {

    private final MemberRepository memberRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (memberRepository.count() > 1) {
            log.info("[Member]더미 데이터 존재");
        } else {
            List<Member> memberList = List.of(
                    new Member(1L, "email1", "name1", Gender.MAN, null, null, 25L),
                    new Member(2L, "email2", "name2", Gender.MAN, null, null, 5L),
                    new Member(3L, "email3", "name3", Gender.MAN, null, null, 6L),
                    new Member(4L, "email4", "name4", Gender.MAN, null, null, 16L)
            );

            memberRepository.saveAll(memberList);
        }
    }
}
