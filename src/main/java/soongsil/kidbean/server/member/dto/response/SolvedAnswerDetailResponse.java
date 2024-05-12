package soongsil.kidbean.server.member.dto.response;

import soongsil.kidbean.server.quiz.application.vo.MorphemeCheckListResponse;
import soongsil.kidbean.server.quiz.domain.AnswerQuizSolved;

public record SolvedAnswerDetailResponse(
        Long solvedId,
        String answerUrl,
        String kidAnswer,
        MorphemeCheckListResponse checkList
) {
    public static SolvedAnswerDetailResponse of(AnswerQuizSolved answerQuizSolved,
                                                MorphemeCheckListResponse checkList) {
        return new SolvedAnswerDetailResponse(
                answerQuizSolved.getSolvedId(),
                answerQuizSolved.getRecordAnswer().getS3Url(),
                answerQuizSolved.getSentenceAnswer(),
                checkList
        );
    }
}
