package soongsil.kidbean.server.mypage.dto.response;

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
                imageQuiz.getImageInfo().getImageUrl(),
                imageQuiz.getAnswer(),
                imageQuizSolved.getAnswer()
        );
    }
}
