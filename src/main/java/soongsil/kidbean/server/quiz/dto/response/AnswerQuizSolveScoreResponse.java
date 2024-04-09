package soongsil.kidbean.server.quiz.dto.response;

public record AnswerQuizSolveScoreResponse(
        Long score
) {
    public static AnswerQuizSolveScoreResponse scoreFrom(Long score) {
        return new AnswerQuizSolveScoreResponse(score);
    }
}
