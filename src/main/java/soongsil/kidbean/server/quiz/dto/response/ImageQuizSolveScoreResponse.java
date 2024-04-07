package soongsil.kidbean.server.quiz.dto.response;

public record ImageQuizSolveScoreResponse(
        Long score
) {
    public static ImageQuizSolveScoreResponse scoreFrom(Long score) {
        return new ImageQuizSolveScoreResponse(score);
    }
}
