package soongsil.kidbean.server.quiz.dto.response;

import java.util.List;
import lombok.Builder;
import soongsil.kidbean.server.quiz.domain.SentenceQuiz;

@Builder
public record SentenceQuizResponse(
        Long quizId,
        String title,
        List<SentenceQuizWordResponse> words
) {
    public static SentenceQuizResponse from(SentenceQuiz sentenceQuiz) {
        return SentenceQuizResponse.builder()
                .quizId(sentenceQuiz.getQuizId())
                .title(sentenceQuiz.getTitle())
                .words(sentenceQuiz.getWords().stream()
                        .map(SentenceQuizWordResponse::from)
                        .toList())
                .build();
    }
}
