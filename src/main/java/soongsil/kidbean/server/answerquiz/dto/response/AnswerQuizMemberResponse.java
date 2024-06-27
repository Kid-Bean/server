package soongsil.kidbean.server.answerquiz.dto.response;

import soongsil.kidbean.server.answerquiz.domain.AnswerQuiz;

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
