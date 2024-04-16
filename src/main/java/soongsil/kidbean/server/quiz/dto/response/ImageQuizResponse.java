package soongsil.kidbean.server.quiz.dto.response;

import soongsil.kidbean.server.quiz.domain.ImageQuiz;

public record ImageQuizResponse(
        Long quizId,
        String category,
        String answer,
        String s3Url,
        String title
) {
    public static ImageQuizResponse from(ImageQuiz imageQuiz) {
        return new ImageQuizResponse(imageQuiz.getQuizId(),
                imageQuiz.getQuizCategory().toString(),
                imageQuiz.getAnswer(),
                imageQuiz.getS3Info().getS3Url(),
                imageQuiz.getTitle());
    }
}
