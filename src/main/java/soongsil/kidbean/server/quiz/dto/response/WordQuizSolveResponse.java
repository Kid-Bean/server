package soongsil.kidbean.server.quiz.dto.response;

import java.util.List;
import lombok.Builder;
import soongsil.kidbean.server.quiz.domain.WordQuiz;

@Builder
public record WordQuizSolveResponse(
        Long quizId,
        String title,
        String answer,
        List<WordResponse> words
) {
    public static WordQuizSolveResponse from(WordQuiz WordQuiz) {
        return WordQuizSolveResponse.builder()
                .quizId(WordQuiz.getQuizId())
                .answer(WordQuiz.getAnswer())
                .title(WordQuiz.getTitle())
                .words(WordQuiz.getWords().stream()
                        .map(WordResponse::from)
                        .toList())
                .build();
    }
}
