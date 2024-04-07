package soongsil.kidbean.server.quiz.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import soongsil.kidbean.server.quiz.domain.type.QuizCategory;

@Builder
public record ImageQuizUpdateRequest(
        @NotNull(message = "제목을 입력해주세요.")
        String title,
        @NotNull(message = "정답을 입력해주세요.")
        String answer,
        QuizCategory quizCategory
) {
}
