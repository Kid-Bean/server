package soongsil.kidbean.server.answerquiz.dto.response;

public record AnswerQuizSolveScoreResponse(
        Long score
) {
    public static AnswerQuizSolveScoreResponse scoreFrom(Long score) {
        return new AnswerQuizSolveScoreResponse(score);
    }
}
