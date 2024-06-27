package soongsil.kidbean.server.imagequiz.dto.response;

import java.util.List;

public record ImageQuizSolveListResponse(
        List<ImageQuizSolveResponse> imageQuizSolveResponseList
) {
}
