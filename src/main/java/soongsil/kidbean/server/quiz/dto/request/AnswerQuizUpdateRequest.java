package soongsil.kidbean.server.quiz.dto.request;

import jakarta.validation.constraints.NotNull;

public record AnswerQuizUpdateRequest(
        @NotNull(message = "제목을 입력해주세요.")
        String title,
        @NotNull(message = "질문을 입력해주세요.")
        String question
) {
}
