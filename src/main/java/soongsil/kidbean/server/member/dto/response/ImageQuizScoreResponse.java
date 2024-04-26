package soongsil.kidbean.server.member.dto.response;

import java.util.List;

public record ImageQuizScoreResponse(
        List<ScoreInfo> myScoreInfo,
        List<ScoreInfo> ageScoreInfo
) {
    public static ImageQuizScoreResponse of(List<ScoreInfo> myScoreInfo, List<ScoreInfo> ageScoreInfo) {
        return new ImageQuizScoreResponse(myScoreInfo, ageScoreInfo);
    }
}
