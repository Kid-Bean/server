package soongsil.kidbean.server.answerquiz.dto.response;

import soongsil.kidbean.server.answerquiz.domain.AnswerQuiz;

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
