package soongsil.kidbean.server.quiz.dto.response;

import soongsil.kidbean.server.quiz.domain.AnswerQuiz;

public record AnswerQuizMemberResponse(
        String title,
        Long quizId
) {
    public static AnswerQuizMemberResponse from(AnswerQuiz answerQuiz) {
        return new AnswerQuizMemberResponse(
                answerQuiz.getTitle(),
                answerQuiz.getQuizId());
    }
}
