package soongsil.kidbean.server.quiz.dto.request;

public record AnswerQuizSolvedRequest(
        Long quizId,
        String answer
) {
}