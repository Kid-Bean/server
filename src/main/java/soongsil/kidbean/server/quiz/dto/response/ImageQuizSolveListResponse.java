package soongsil.kidbean.server.quiz.dto.response;

import java.util.List;

public record ImageQuizSolveListResponse(
        List<ImageQuizSolveResponse> imageQuizSolveResponseList
) {
}
