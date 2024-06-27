package soongsil.kidbean.server.quizsolve.application.quizsolver.dto;

import soongsil.kidbean.server.quizsolve.domain.type.QuizCategory;

public record SolvedQuizInfo(
        QuizCategory category,
        Long score,
        boolean isExist
) {
}
