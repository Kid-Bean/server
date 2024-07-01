package soongsil.kidbean.server.wordquiz.dto.response;

import java.util.List;
import lombok.Builder;
import soongsil.kidbean.server.wordquiz.domain.WordQuiz;

@Builder
public record WordQuizSolveResponse(
        Long quizId,
        String title,
        String answer,
        List<WordResponse> words
) {
    public static WordQuizSolveResponse from(WordQuiz wordQuiz) {
        return WordQuizSolveResponse.builder()
                .quizId(wordQuiz.getQuizId())
                .answer(wordQuiz.getAnswer())
                .title(wordQuiz.getTitle())
                .words(wordQuiz.getWords().stream()
                        .map(WordResponse::from)
                        .toList())
                .build();
    }
}
