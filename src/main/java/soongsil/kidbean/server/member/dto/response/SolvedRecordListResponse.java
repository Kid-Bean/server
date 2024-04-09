package soongsil.kidbean.server.member.dto.response;

import java.util.List;

public record SolvedRecordListResponse(
        List<SolvedRecordInfo> solvedList
) {
    public static SolvedRecordListResponse from(List<SolvedRecordInfo> solvedList) {
        return new SolvedRecordListResponse(solvedList);
    }
}
