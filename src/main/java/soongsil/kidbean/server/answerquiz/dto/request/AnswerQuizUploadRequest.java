package soongsil.kidbean.server.answerquiz.dto.request;

import jakarta.validation.constraints.NotNull;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.answerquiz.domain.AnswerQuiz;

public record AnswerQuizUploadRequest(
        @NotNull(message = "제목을 입력해주세요.")
        String title,
        @NotNull(message = "질문을 입력해주세요.")
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
