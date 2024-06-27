package soongsil.kidbean.server.wordquiz.dto.response;

import soongsil.kidbean.server.wordquiz.domain.WordQuiz;

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
