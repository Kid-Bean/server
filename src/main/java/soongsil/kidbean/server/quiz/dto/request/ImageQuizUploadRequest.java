package soongsil.kidbean.server.quiz.dto.request;

import lombok.Builder;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quiz.domain.ImageQuiz;
import soongsil.kidbean.server.quiz.domain.type.Category;

@Builder
public record ImageQuizUploadRequest(
        String title,
        String answer,
        Category category,
        Member member
) {
    public ImageQuiz toImageQuiz(Member member) {
        return ImageQuiz
                .builder()
                .member(member)
                .title(title)
                .answer(answer)
                .category(category)
                .build();
    }
}