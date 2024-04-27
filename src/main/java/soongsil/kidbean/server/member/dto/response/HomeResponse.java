package soongsil.kidbean.server.member.dto.response;

import soongsil.kidbean.server.global.vo.S3Info;
import soongsil.kidbean.server.member.domain.Member;

import java.time.LocalDate;

public record HomeResponse(
        String name,
        LocalDate joinDate,
        String s3Url,
        Long score
) {
    public static HomeResponse from(Member member, S3Info s3Info) {
        return new HomeResponse(
                member.getName(),
                member.getJoinDate(),
                s3Info.getS3Url(),
                member.getScore());
    }
}
