package soongsil.kidbean.server.member.dto.response;

import java.time.LocalDate;
import java.time.Period;
import soongsil.kidbean.server.member.domain.Member;

public record MemberInfoResponse(
        Long memberId,
        LocalDate memberBirth,
        Integer memberAge
) {
    public static MemberInfoResponse from(Member member) {
        int age = Period.between(member.getBirthDate(), LocalDate.now()).getYears();

        return new MemberInfoResponse(
                member.getMemberId(),
                member.getBirthDate(),
                age
        );
    }
}
