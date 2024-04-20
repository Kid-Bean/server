package soongsil.kidbean.server.quiz.dto.request;

import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quiz.domain.AnswerQuiz;

public record AnswerQuizUploadRequest(
        String title,
        String question
) {
    public AnswerQuiz toAnswerQuiz(Member member) {
        return AnswerQuiz
                .builder()
                .title(title)
                .question(question)
                .member(member)
                .build();
    }
}
