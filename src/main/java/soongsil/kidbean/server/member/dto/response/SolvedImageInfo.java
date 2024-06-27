package soongsil.kidbean.server.member.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import soongsil.kidbean.server.quizsolve.domain.QuizSolved;
import soongsil.kidbean.server.quizsolve.domain.type.QuizCategory;

@Builder
public record SolvedImageInfo(
        Long solvedId,
        QuizCategory quizCategory,
        String answer,
        LocalDateTime solvedTime
) {
    public static SolvedImageInfo from(QuizSolved quizSolved) {
        return new SolvedImageInfo(
                quizSolved.getSolvedId(),
                quizSolved.getImageQuiz().getQuizCategory(),
                quizSolved.getImageQuiz().getAnswer(),
                quizSolved.getCreatedDate()
        );
    }
}
