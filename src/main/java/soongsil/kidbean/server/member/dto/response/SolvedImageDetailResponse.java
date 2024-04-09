package soongsil.kidbean.server.member.dto.response;

import soongsil.kidbean.server.quiz.domain.ImageQuiz;
import soongsil.kidbean.server.quiz.domain.ImageQuizSolved;

public record SolvedImageDetailResponse(
        Long solvedId,
        String imageUrl,
        String answer,
        String kidAnswer
) {
    public static SolvedImageDetailResponse from(ImageQuizSolved imageQuizSolved) {
        ImageQuiz imageQuiz = imageQuizSolved.getImageQuiz();

        return new SolvedImageDetailResponse(
                imageQuizSolved.getSolvedId(),
                imageQuiz.getS3Info().getS3Url(),
                imageQuiz.getAnswer(),
                imageQuizSolved.getAnswer()
        );
    }
}
