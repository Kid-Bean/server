package soongsil.kidbean.server.quiz.dto.request;

import jakarta.validation.constraints.NotNull;

public record AnswerQuizSolvedRequest(
        @NotNull(message = "퀴즈 아이디를 입력해주세요.")
        Long quizId,
        @NotNull(message = "정답을 입력해주세요.")
        String answer
) {
}