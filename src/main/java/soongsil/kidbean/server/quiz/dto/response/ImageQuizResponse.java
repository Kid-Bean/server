package soongsil.kidbean.server.quiz.dto.response;

import soongsil.kidbean.server.quiz.domain.ImageQuiz;

public record ImageQuizResponse(
        Long quizId,
        String category,
        String answer,
        String imageUrl,
        String title
) {
    public static ImageQuizResponse from(ImageQuiz imageQuiz) {
        return new ImageQuizResponse(imageQuiz.getQuizId(),
                imageQuiz.getCategory().toString(),
                imageQuiz.getAnswer(),
                imageQuiz.getImageInfo().getImageUrl(),
                imageQuiz.getTitle());
    }
}
