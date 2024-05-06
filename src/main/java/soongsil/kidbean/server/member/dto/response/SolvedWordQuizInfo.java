package soongsil.kidbean.server.member.dto.response;

import java.time.LocalDateTime;
import soongsil.kidbean.server.quiz.domain.QuizSolved;
import soongsil.kidbean.server.quiz.domain.type.QuizCategory;

public record SolvedWordQuizInfo(
        Long solvedId,
        LocalDateTime solvedTime,
        QuizCategory quizCategory,
        String title
) {
    public static SolvedWordQuizInfo from(QuizSolved wordQuizSolved) {
        return new SolvedWordQuizInfo(
                wordQuizSolved.getSolvedId(),
                wordQuizSolved.getSolvedTime(),
                wordQuizSolved.getQuizCategory(),
                wordQuizSolved.getWordQuiz().getTitle()
        );
    }
}
