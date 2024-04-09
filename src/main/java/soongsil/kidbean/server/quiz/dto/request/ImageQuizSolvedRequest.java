package soongsil.kidbean.server.quiz.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quiz.domain.ImageQuiz;
import soongsil.kidbean.server.quiz.domain.ImageQuizSolved;

@Builder
public record ImageQuizSolvedRequest(
        @NotNull(message = "퀴즈 아이디를 입력해주세요.")
        Long imageQuizId,
        @NotNull(message = "정답을 입력해주세요.")
        String answer
) {
    public ImageQuizSolved toImageQuizSolved(ImageQuiz imageQuiz, Member member) {
        return ImageQuizSolved.builder()
                .imageQuiz(imageQuiz)
                .answer(answer)
                .member(member)
                .build();
    }
}
