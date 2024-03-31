package soongsil.kidbean.server.mypage.dto.response;

import java.util.List;

public record SolvedRecordListResponse(
        List<SolvedRecordInfo> solvedList
) {
    public static SolvedRecordListResponse from(List<SolvedRecordInfo> solvedList) {
        return new SolvedRecordListResponse(solvedList);
    }
}
