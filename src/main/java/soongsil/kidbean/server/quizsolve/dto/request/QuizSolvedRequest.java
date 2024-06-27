package soongsil.kidbean.server.quizsolve.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.imagequiz.domain.ImageQuiz;
import soongsil.kidbean.server.quizsolve.domain.QuizSolved;
import soongsil.kidbean.server.wordquiz.domain.WordQuiz;

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
