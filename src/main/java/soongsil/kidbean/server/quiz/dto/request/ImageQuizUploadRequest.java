package soongsil.kidbean.server.quiz.dto.request;

import lombok.Builder;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quiz.domain.ImageQuiz;
import soongsil.kidbean.server.quiz.domain.type.QuizCategory;

@Builder
public record ImageQuizUploadRequest(
        String title,
        String answer,
        QuizCategory quizCategory,
        Member member
) {
    public ImageQuiz toImageQuiz(Member member) {
        return ImageQuiz
                .builder()
                .member(member)
                .title(title)
                .answer(answer)
                .quizCategory(quizCategory)
                .build();
    }
}
