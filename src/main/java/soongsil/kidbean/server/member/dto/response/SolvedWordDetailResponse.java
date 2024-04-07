package soongsil.kidbean.server.member.dto.response;

import soongsil.kidbean.server.quiz.domain.Analysis;
import soongsil.kidbean.server.quiz.domain.AnswerQuizSolved;

public record SolvedWordDetailResponse(
        Long solvedId,
        String question,
        String kidAnswer,
        String analysisReport
) {
    public static SolvedWordDetailResponse of(AnswerQuizSolved answerQuizSolved, Analysis analysis) {
        return new SolvedWordDetailResponse(
                answerQuizSolved.getSolvedId(),
                answerQuizSolved.getRecordAnswer().getS3Url(),
                answerQuizSolved.getWordAnswer(),
                analysis.toString() //TODO 수정해야함
        );
    }
}
