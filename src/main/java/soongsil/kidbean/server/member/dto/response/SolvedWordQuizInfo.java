package soongsil.kidbean.server.member.dto.response;

import java.time.LocalDateTime;
import soongsil.kidbean.server.quiz.domain.QuizSolved;

public record SolvedWordQuizInfo(
        Long solvedId,
        LocalDateTime solvedTime
) {
    public static SolvedWordQuizInfo from(QuizSolved wordQuizSolved) {
        return new SolvedWordQuizInfo(
                wordQuizSolved.getSolvedId(),
                wordQuizSolved.getSolvedTime()
        );
    }
}
