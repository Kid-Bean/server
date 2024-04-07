package soongsil.kidbean.server.quiz.dto.response;

import lombok.Builder;
import soongsil.kidbean.server.quiz.domain.WordQuiz;

import java.util.List;

@Builder
public record WordQuizMemberDetailResponse(
        String title,
        String answer,
        List<WordResponse> words
) {
    public static WordQuizMemberDetailResponse from(WordQuiz WordQuiz) {
        return WordQuizMemberDetailResponse
                .builder()
                .title(WordQuiz.getTitle())
                .answer(WordQuiz.getAnswer())
                .words(WordQuiz.getWords()
                        .stream()
                        .map(WordResponse::from)
                        .toList())
                .build();
    }
}
