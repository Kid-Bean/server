package soongsil.kidbean.server.imagequiz.dto.response;

import soongsil.kidbean.server.imagequiz.domain.ImageQuiz;

public record ImageQuizMemberResponse(
        String title,
        Long quizId
) {
    public static ImageQuizMemberResponse from(ImageQuiz imageQuiz) {
        return new ImageQuizMemberResponse(
                imageQuiz.getTitle(),
                imageQuiz.getQuizId());
    }
}
