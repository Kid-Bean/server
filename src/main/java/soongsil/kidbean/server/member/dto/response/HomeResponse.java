package soongsil.kidbean.server.member.dto.response;

import soongsil.kidbean.server.member.domain.Member;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record HomeResponse(
        String name,
        LocalDateTime createdDate,
        String s3Url,
        Long score
) {
    public static HomeResponse from(Member member, String s3Url) {
        return new HomeResponse(
                member.getName(),
                member.getCreatedDate(),
                s3Url,
                member.getScore());
    }
}
