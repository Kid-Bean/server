package soongsil.kidbean.server.quiz.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quiz.domain.ImageQuiz;
import soongsil.kidbean.server.quiz.domain.QuizSolved;
import soongsil.kidbean.server.quiz.domain.WordQuiz;

@Builder
public record QuizSolvedRequest(
        @NotNull(message = "퀴즈 아이디를 입력해주세요.")
        Long quizId,
        @NotNull(message = "정답을 입력해주세요.")
        String answer
) {
    public QuizSolved toQuizSolved(ImageQuiz imageQuiz, Member member) {
        return QuizSolved.builder()
                .imageQuiz(imageQuiz)
                .reply(answer)
                .member(member)
                .build();
    }

    public QuizSolved toQuizSolved(WordQuiz wordQuiz, Member member) {
        return QuizSolved.builder()
                .wordQuiz(wordQuiz)
                .reply(answer)
                .member(member)
                .build();
    }
}
