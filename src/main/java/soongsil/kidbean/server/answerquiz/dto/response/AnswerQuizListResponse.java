package soongsil.kidbean.server.answerquiz.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record AnswerQuizListResponse(
        List<AnswerQuizResponse> answerQuizList
) {
    public static AnswerQuizListResponse from(List<AnswerQuizResponse> responses) {
        return AnswerQuizListResponse.builder()
                .answerQuizList(responses)
                .build();
    }
}
