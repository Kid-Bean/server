package soongsil.kidbean.server.mypage.dto.response;

import java.time.LocalDateTime;
import soongsil.kidbean.server.quiz.domain.RecordQuizSolved;

public record SolvedRecordInfo(
        Long solvedId,
        LocalDateTime solvedTime
) {
    public static SolvedRecordInfo from(RecordQuizSolved recordQuizSolved) {
        return new SolvedRecordInfo(
                recordQuizSolved.getSolvedId(),
                recordQuizSolved.getSolvedTime()
        );
    }
}
