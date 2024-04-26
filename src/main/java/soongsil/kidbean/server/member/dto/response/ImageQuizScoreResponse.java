package soongsil.kidbean.server.member.dto.response;

import java.util.List;

public record ImageQuizScoreResponse(
        List<MyScoreInfo> myScoreInfo,
        List<AgeScoreInfo> ageScoreInfo
) {
    public static ImageQuizScoreResponse of(List<MyScoreInfo> myScoreInfo, List<AgeScoreInfo> ageScoreInfo) {
        return new ImageQuizScoreResponse(myScoreInfo, ageScoreInfo);
    }
}
