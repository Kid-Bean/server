package soongsil.kidbean.server.wordquiz.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record WordQuizSolveListResponse(
        List<WordQuizSolveResponse> wordQuizSolveResponseList
) {
    public static WordQuizSolveListResponse from(List<WordQuizSolveResponse> wordQuizSolveResponseList) {
        return WordQuizSolveListResponse.builder()
                .wordQuizSolveResponseList(wordQuizSolveResponseList)
                .build();
    }
}