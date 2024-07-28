package soongsil.kidbean.server.wordquiz.dto.response;

import java.util.List;

public record WordQuizSolveListResponse(
        List<WordQuizSolveResponse> wordQuizSolveResponseList
) {
}
