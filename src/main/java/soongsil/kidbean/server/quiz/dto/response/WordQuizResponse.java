package soongsil.kidbean.server.quiz.dto.response;

import java.util.List;
import lombok.Builder;
import soongsil.kidbean.server.quiz.domain.WordQuiz;

@Builder
public record WordQuizResponse(
        Long quizId,
        String title,
        String answer,
        List<WordResponse> words
) {
    public static WordQuizResponse from(WordQuiz WordQuiz) {
        return WordQuizResponse.builder()
                .quizId(WordQuiz.getQuizId())
                .answer(WordQuiz.getAnswer())
                .title(WordQuiz.getTitle())
                .words(WordQuiz.getWords().stream()
                        .map(WordResponse::from)
                        .toList())
                .build();
    }
}
