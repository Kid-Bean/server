package soongsil.kidbean.server.quiz.dto.response;

public record WordQuizSolveScoreResponse(
        Long score
) {
    public static WordQuizSolveScoreResponse scoreFrom(Long score) {
        return new WordQuizSolveScoreResponse(score);
    }
}
