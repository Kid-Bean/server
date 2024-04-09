package soongsil.kidbean.server.member.dto.response;

import java.util.List;
import soongsil.kidbean.server.quiz.domain.ImageQuizSolved;

public record SolvedWordQuizListResponse(
        List<SolvedWordQuizInfo> solvedList
) {
    public static SolvedWordQuizListResponse from(List<SolvedWordQuizInfo> wordQuizSolvedList) {
        return new SolvedWordQuizListResponse(wordQuizSolvedList);
    }
}
