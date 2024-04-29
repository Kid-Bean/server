package soongsil.kidbean.server.member.dto.response;

import soongsil.kidbean.server.member.domain.Member;

import java.time.LocalDate;

public record HomeResponse(
        String name,
        LocalDate createDate,
        String s3Url,
        Long score
) {
    public static HomeResponse from(Member member, String s3Url) {
        return new HomeResponse(
                member.getName(),
                member.getCreateDate(),
                s3Url,
                member.getScore());
    }
}
