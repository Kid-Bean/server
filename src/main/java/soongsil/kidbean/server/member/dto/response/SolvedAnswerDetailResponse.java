package soongsil.kidbean.server.member.dto.response;

import java.util.List;
import soongsil.kidbean.server.quiz.application.vo.ApiResponseVO.ReturnObject.Sentence.MorphemeVO;
import soongsil.kidbean.server.quiz.application.vo.MorphemeCheckListResponse;
import soongsil.kidbean.server.quiz.domain.AnswerQuizSolved;
import soongsil.kidbean.server.quiz.domain.Morpheme;

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
