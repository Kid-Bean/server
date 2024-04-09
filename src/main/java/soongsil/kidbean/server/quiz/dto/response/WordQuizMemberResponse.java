package soongsil.kidbean.server.quiz.dto.response;

import soongsil.kidbean.server.quiz.domain.WordQuiz;

public record WordQuizMemberResponse(
        String title,
        Long quizId
) {
    public static WordQuizMemberResponse from(WordQuiz wordQuiz) {
        return new WordQuizMemberResponse(
                wordQuiz.getTitle(),
                wordQuiz.getQuizId());
    }
}
