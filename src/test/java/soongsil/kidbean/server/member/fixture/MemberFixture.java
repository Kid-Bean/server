package soongsil.kidbean.server.member.fixture;

import static soongsil.kidbean.server.member.domain.type.Gender.MAN;

import org.springframework.test.util.ReflectionTestUtils;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.domain.type.Role;

public class MemberFixture {

    public final static Member MEMBER1 = Member.builder()
            .gender(MAN)
            .name("asd")
            .score(21L)
            .role(Role.MEMBER)
            .socialId("socialIdMember")
            .email("asd@naver.com")
            .build();

    public final static Member MEMBER2 = Member.builder()
            .gender(MAN)
            .name("asd")
            .score(21L)
            .role(Role.MEMBER)
            .socialId("socialIdMember")
            .email("asd@naver.com")
            .build();

    public final static Member ADMIN = Member.builder()
            .gender(MAN)
            .name("asd")
            .score(21L)
            .role(Role.ADMIN)
            .socialId("socialIdMember")
            .email("asd@naver.com")
            .build();

    static {
        ReflectionTestUtils.setField(MEMBER1, "memberId", 1L);
        ReflectionTestUtils.setField(MEMBER2, "memberId", 2L);
        ReflectionTestUtils.setField(ADMIN, "memberId", 3L);
    }
}
