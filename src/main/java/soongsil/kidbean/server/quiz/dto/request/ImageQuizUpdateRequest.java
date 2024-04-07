package soongsil.kidbean.server.quiz.dto.request;

import lombok.Builder;
import soongsil.kidbean.server.quiz.domain.type.Category;

@Builder
public record ImageQuizUpdateRequest(
        String title,
        String answer,
        Category category
) {
}
