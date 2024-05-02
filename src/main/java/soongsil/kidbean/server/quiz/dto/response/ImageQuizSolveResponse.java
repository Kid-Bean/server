package soongsil.kidbean.server.quiz.dto.response;

import lombok.Builder;
import soongsil.kidbean.server.quiz.domain.ImageQuiz;

@Builder
public record ImageQuizSolveResponse(
        Long quizId,
        String answer,
        String s3Url
) {
    public static ImageQuizSolveResponse from(ImageQuiz imageQuiz) {
        return ImageQuizSolveResponse.builder()
                .answer(imageQuiz.getAnswer())
                .quizId(imageQuiz.getQuizId())
                .s3Url(imageQuiz.getS3Info().getS3Url())
                .build();
    }
}
