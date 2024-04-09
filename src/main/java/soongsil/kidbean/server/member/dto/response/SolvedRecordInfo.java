package soongsil.kidbean.server.member.dto.response;

import java.time.LocalDateTime;
import soongsil.kidbean.server.quiz.domain.AnswerQuizSolved;

public record SolvedRecordInfo(
        Long solvedId,
        LocalDateTime solvedTime
) {
    public static SolvedRecordInfo from(AnswerQuizSolved answerQuizSolved) {
        return new SolvedRecordInfo(
                answerQuizSolved.getSolvedId(),
                answerQuizSolved.getSolvedTime()
        );
    }
}
