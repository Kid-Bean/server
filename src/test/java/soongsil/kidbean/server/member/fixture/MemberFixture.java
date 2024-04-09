package soongsil.kidbean.server.member.fixture;

import static soongsil.kidbean.server.member.domain.type.Gender.MAN;

import org.springframework.test.util.ReflectionTestUtils;
import soongsil.kidbean.server.member.domain.Member;

public class MemberFixture {

    public final static Member MEMBER = Member.builder()
            .gender(MAN)
            .name("asd")
            .score(21L)
            .email("asd@naver.com")
            .build();

    static {
        ReflectionTestUtils.setField(MEMBER, "memberId", 1L);
    }
}
