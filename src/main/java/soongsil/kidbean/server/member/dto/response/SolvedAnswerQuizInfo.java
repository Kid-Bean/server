package soongsil.kidbean.server.member.dto.response;

import java.time.LocalDateTime;
import soongsil.kidbean.server.quiz.domain.AnswerQuizSolved;

public record SolvedAnswerQuizInfo(
        Long solvedId,
        LocalDateTime solvedTime,
        String title
) {
    public static SolvedAnswerQuizInfo from(AnswerQuizSolved answerQuizSolved) {
        return new SolvedAnswerQuizInfo(
                answerQuizSolved.getSolvedId(),
                answerQuizSolved.getCreatedDate(),
                answerQuizSolved.getAnswerQuiz().getTitle()
        );
    }
}
