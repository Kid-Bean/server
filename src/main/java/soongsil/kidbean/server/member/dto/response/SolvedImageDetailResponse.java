package soongsil.kidbean.server.member.dto.response;

import soongsil.kidbean.server.quiz.domain.ImageQuiz;
import soongsil.kidbean.server.quiz.domain.ImageQuizSolved;
import soongsil.kidbean.server.quiz.domain.QuizSolved;

public record SolvedImageDetailResponse(
        Long solvedId,
        String imageUrl,
        String answer,
        String kidAnswer
) {
    public static SolvedImageDetailResponse from(QuizSolved quizSolved) {
        ImageQuiz imageQuiz = quizSolved.getImageQuiz();

        return new SolvedImageDetailResponse(
                quizSolved.getSolvedId(),
                imageQuiz.getS3Info().getS3Url(),
                imageQuiz.getAnswer(),
                quizSolved.getReply()
        );
    }
}
