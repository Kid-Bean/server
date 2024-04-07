package soongsil.kidbean.server.mypage.dto.response;

import soongsil.kidbean.server.quiz.domain.Analysis;
import soongsil.kidbean.server.quiz.domain.RecordQuizSolved;

public record SolvedSentenceDetailResponse(
        Long solvedId,
        String question,
        String kidAnswer,
        String analysisReport
) {
    public static SolvedSentenceDetailResponse of(RecordQuizSolved recordQuizSolved, Analysis analysis) {
        return new SolvedSentenceDetailResponse(
                recordQuizSolved.getSolvedId(),
                recordQuizSolved.getRecordAnswer(),
                recordQuizSolved.getSentenceAnswer(),
                analysis.toString() //TODO 수정해야함
        );
    }
}
