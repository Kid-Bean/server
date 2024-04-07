package soongsil.kidbean.server.quiz.dto.request;

import lombok.Builder;
import soongsil.kidbean.server.quiz.domain.type.QuizCategory;

@Builder
public record ImageQuizUpdateRequest(
        String title,
        String answer,
        QuizCategory quizCategory
) {
}
