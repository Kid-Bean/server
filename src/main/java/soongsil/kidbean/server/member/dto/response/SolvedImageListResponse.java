package soongsil.kidbean.server.member.dto.response;

import java.util.List;

public record SolvedImageListResponse(
        List<SolvedImageInfo> solvedList
) {
    public static SolvedImageListResponse from(List<SolvedImageInfo> solvedList) {
        return new SolvedImageListResponse(solvedList);
    }
}
