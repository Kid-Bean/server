package soongsil.kidbean.server.member.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import soongsil.kidbean.server.quiz.domain.QuizSolved;

@Builder
public record SolvedImageInfo(
        Long solvedId,
        LocalDateTime solvedTime
) {
    public static SolvedImageInfo from(QuizSolved quizSolved) {
        return new SolvedImageInfo(
                quizSolved.getSolvedId(),
                quizSolved.getSolvedTime()
        );
    }
}
