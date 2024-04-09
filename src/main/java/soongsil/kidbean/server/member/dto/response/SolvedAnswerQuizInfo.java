package soongsil.kidbean.server.member.dto.response;

import java.time.LocalDateTime;
import soongsil.kidbean.server.quiz.domain.AnswerQuizSolved;

public record SolvedAnswerQuizInfo(
        Long solvedId,
        LocalDateTime solvedTime
) {
    public static SolvedAnswerQuizInfo from(AnswerQuizSolved answerQuizSolved) {
        return new SolvedAnswerQuizInfo(
                answerQuizSolved.getSolvedId(),
                answerQuizSolved.getSolvedTime()
        );
    }
}