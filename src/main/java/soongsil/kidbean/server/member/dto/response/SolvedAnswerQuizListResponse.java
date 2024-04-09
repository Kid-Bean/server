package soongsil.kidbean.server.member.dto.response;

import java.util.List;

public record SolvedAnswerQuizListResponse(
        List<SolvedAnswerQuizInfo> solvedList
) {
    public static SolvedAnswerQuizListResponse from(List<SolvedAnswerQuizInfo> solvedList) {
        return new SolvedAnswerQuizListResponse(solvedList);
    }
}
