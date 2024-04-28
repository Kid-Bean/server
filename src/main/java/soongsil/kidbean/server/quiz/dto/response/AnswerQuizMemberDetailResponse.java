package soongsil.kidbean.server.quiz.dto.response;

import soongsil.kidbean.server.quiz.domain.AnswerQuiz;

public record AnswerQuizMemberDetailResponse(
        String title,
        String question
) {
    public static AnswerQuizMemberDetailResponse from(AnswerQuiz answerQuiz) {
        return new AnswerQuizMemberDetailResponse(
                answerQuiz.getTitle(),
                answerQuiz.getQuestion());
    }
}
