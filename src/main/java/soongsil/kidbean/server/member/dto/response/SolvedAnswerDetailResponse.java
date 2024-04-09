package soongsil.kidbean.server.member.dto.response;

import java.util.List;
import soongsil.kidbean.server.quiz.domain.AnswerQuizSolved;
import soongsil.kidbean.server.quiz.domain.Morpheme;
import soongsil.kidbean.server.quiz.domain.UseWord;

public record SolvedAnswerDetailResponse(
        Long solvedId,
        String question,
        String kidAnswer,
        List<UseWordInfo> useWordInfo,
        List<MorphemeInfo> morphemeInfo //TODO
) {
    public static SolvedAnswerDetailResponse of(AnswerQuizSolved answerQuizSolved, List<Morpheme> morphemeList,
                                                List<UseWord> useWordList) {
        return new SolvedAnswerDetailResponse(
                answerQuizSolved.getSolvedId(),
                answerQuizSolved.getRecordAnswer().getS3Url(),
                answerQuizSolved.getSentenceAnswer(),
                useWordList.stream()
                        .map(UseWordInfo::from)
                        .toList(),
                null //TODO
        );
    }
}
