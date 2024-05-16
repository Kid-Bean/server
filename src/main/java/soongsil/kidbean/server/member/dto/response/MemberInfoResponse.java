package soongsil.kidbean.server.member.dto.response;

import java.time.LocalDate;
import java.time.Period;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.domain.type.Gender;

public record MemberInfoResponse(
        Long memberId,
        LocalDate memberBirth,
        String memberName,
        Integer memberAge,
        Gender memberGender
) {
    public static MemberInfoResponse from(Member member) {
        int age = Period.between(member.getBirthDate(), LocalDate.now()).getYears();

        return new MemberInfoResponse(
                member.getMemberId(),
                member.getBirthDate(),
                member.getName(),
                age,
                member.getGender()
        );
    }
}
