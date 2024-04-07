package soongsil.kidbean.server.quiz.dto.response;

import soongsil.kidbean.server.quiz.domain.AnswerQuiz;

public record AnswerQuizResponse(
        Long quizId,
        String question
) {
    public static AnswerQuizResponse from(AnswerQuiz answerQuiz) {
        return new AnswerQuizResponse(answerQuiz.getQuizId(), answerQuiz.getQuestion());
    }
}
