package soongsil.kidbean.server.member.dto.request;

import jakarta.validation.constraints.NotNull;
import soongsil.kidbean.server.member.domain.type.Gender;

import java.time.LocalDate;

public record MemberInfoRequest(
        @NotNull(message = "이름을 입력해주세요.")
        String name,
        @NotNull(message = "성별을 선택해주세요.")
        Gender gender,
        @NotNull(message = "생일을 입력해주세요.")
        LocalDate birthDate
) {
}
