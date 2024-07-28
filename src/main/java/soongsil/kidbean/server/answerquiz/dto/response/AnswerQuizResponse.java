package soongsil.kidbean.server.answerquiz.dto.response;

import lombok.Builder;
import soongsil.kidbean.server.answerquiz.domain.AnswerQuiz;

@Builder
public record AnswerQuizResponse(
        Long quizId,
        String question,
        String title
) {
    public static AnswerQuizResponse from(AnswerQuiz answerQuiz) {
        return AnswerQuizResponse.builder()
                .quizId(answerQuiz.getQuizId())
                .title(answerQuiz.getTitle())
                .question(answerQuiz.getQuestion())
                .build();
    }
}
