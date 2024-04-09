package soongsil.kidbean.server.member.dto.response;

import soongsil.kidbean.server.quiz.domain.AnswerQuizSolved;

public record SolvedWordDetailResponse(
        Long solvedId,
        String question,
        String kidAnswer
) {
    public static SolvedWordDetailResponse of(AnswerQuizSolved answerQuizSolved) {
        return null;
    }
}
