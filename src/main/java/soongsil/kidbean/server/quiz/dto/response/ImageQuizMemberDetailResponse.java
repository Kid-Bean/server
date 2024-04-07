package soongsil.kidbean.server.quiz.dto.response;

import lombok.Builder;
import soongsil.kidbean.server.quiz.domain.ImageQuiz;
import soongsil.kidbean.server.quiz.domain.type.QuizCategory;

@Builder
public record ImageQuizMemberDetailResponse(
        String title,
        String imageUrl,
        String answer,
        QuizCategory quizCategory
) {
    public static ImageQuizMemberDetailResponse from(ImageQuiz imageQuiz) {
        return ImageQuizMemberDetailResponse
                .builder()
                .title(imageQuiz.getTitle())
                .imageUrl(imageQuiz.getS3Info().getS3Url())
                .answer(imageQuiz.getAnswer())
                .quizCategory(imageQuiz.getQuizCategory())
                .build();
    }
}
