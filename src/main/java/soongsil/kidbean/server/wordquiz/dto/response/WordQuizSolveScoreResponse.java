package soongsil.kidbean.server.wordquiz.dto.response;

public record WordQuizSolveScoreResponse(
        Long score
) {
    public static WordQuizSolveScoreResponse scoreFrom(Long score) {
        return new WordQuizSolveScoreResponse(score);
    }
}
