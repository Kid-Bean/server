package soongsil.kidbean.server.quiz.dto.response;

import lombok.Builder;
import soongsil.kidbean.server.quiz.domain.ImageQuiz;
import soongsil.kidbean.server.quiz.domain.type.Category;

@Builder
public record ImageQuizMemberDetailResponse(
        String title,
        String imageUrl,
        String answer,
        Category category
) {
    public static ImageQuizMemberDetailResponse from(ImageQuiz imageQuiz) {
        return ImageQuizMemberDetailResponse
                .builder()
                .title(imageQuiz.getTitle())
                .imageUrl(imageQuiz.getS3Info().getS3Url())
                .answer(imageQuiz.getAnswer())
                .category(imageQuiz.getCategory())
                .build();
    }
}
