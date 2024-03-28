package soongsil.kidbean.server.member.fixture;

import soongsil.kidbean.server.member.domain.Member;

public class MemberFixture {

    public final static Member MEMBER = Member.builder()
            .memberId(1L)
            .name("asd")
            .score(21L)
            .email("asd@naver.com")
            .build();
}
