package soongsil.kidbean.server.member.fixture;

import static soongsil.kidbean.server.member.domain.type.Gender.MAN;

import soongsil.kidbean.server.member.domain.Member;

public class MemberFixture {

    public final static Member MEMBER = Member.builder()
            .memberId(1L)
            .gender(MAN)
            .name("asd")
            .score(21L)
            .email("asd@naver.com")
            .build();
}
