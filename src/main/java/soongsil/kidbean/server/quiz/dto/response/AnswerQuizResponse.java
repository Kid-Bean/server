package soongsil.kidbean.server.quiz.dto.response;

import lombok.Builder;
import soongsil.kidbean.server.quiz.domain.AnswerQuiz;

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
