package soongsil.kidbean.server.quiz.dto.response;

import lombok.Builder;
import soongsil.kidbean.server.quiz.domain.SentenceQuiz;
import soongsil.kidbean.server.quiz.domain.SentenceQuizWord;

import java.util.List;

@Builder
public record SentenceQuizMemberDetailResponse(
    String title,
    List<SentenceQuizWordResponse> words
) {
    public static SentenceQuizMemberDetailResponse from(SentenceQuiz sentenceQuiz) {
        return SentenceQuizMemberDetailResponse
                .builder()
                .title(sentenceQuiz.getTitle())
                .words(sentenceQuiz.getWords()
                        .stream()
                        .map(SentenceQuizWordResponse::from)
                        .toList())
                .build();
    }
}
