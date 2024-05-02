package soongsil.kidbean.server.quiz.application.quizsolver.dto;

import soongsil.kidbean.server.quiz.domain.type.QuizCategory;

public record SolvedQuizInfo(
        QuizCategory category,
        Long score
) {
}
