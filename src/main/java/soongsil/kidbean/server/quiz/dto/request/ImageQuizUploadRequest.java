package soongsil.kidbean.server.quiz.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quiz.domain.ImageQuiz;
import soongsil.kidbean.server.quiz.domain.type.Level;
import soongsil.kidbean.server.quiz.domain.type.QuizCategory;

@Builder
public record ImageQuizUploadRequest(
        @NotNull(message = "제목을 입력해주세요.")
        String title,
        @NotNull(message = "정답을 입력해주세요.")
        String answer,
        QuizCategory quizCategory
) {
    public ImageQuiz toImageQuiz(Member member) {
        return ImageQuiz
                .builder()
                .member(member)
                .title(title)
                .answer(answer)
                .quizCategory(quizCategory)
                .level(Level.BRONZE)
                .build();
    }
}
