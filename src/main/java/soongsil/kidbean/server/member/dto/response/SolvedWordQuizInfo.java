package soongsil.kidbean.server.member.dto.response;

import java.time.LocalDateTime;
import soongsil.kidbean.server.quizsolve.domain.QuizSolved;
import soongsil.kidbean.server.quizsolve.domain.type.QuizCategory;

public record SolvedWordQuizInfo(
        Long solvedId,
        LocalDateTime solvedTime,
        QuizCategory quizCategory,
        String title
) {
    public static SolvedWordQuizInfo from(QuizSolved wordQuizSolved) {
        return new SolvedWordQuizInfo(
                wordQuizSolved.getSolvedId(),
                wordQuizSolved.getCreatedDate(),
                wordQuizSolved.getQuizCategory(),
                wordQuizSolved.getWordQuiz().getTitle()
        );
    }
}
