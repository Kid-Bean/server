package soongsil.kidbean.server.member.dto.request;

import soongsil.kidbean.server.member.domain.type.Gender;

import java.time.LocalDate;

public record MemberInfoRequest(
        String name,
        Gender gender,
        LocalDate birthDate
) {
}
