package soongsil.kidbean.server.quiz.dto.response;

import lombok.Builder;
import soongsil.kidbean.server.quiz.domain.ImageQuiz;

public record ImageQuizMemberResponse (
    String title
) {
    public static ImageQuizMemberResponse from(ImageQuiz imageQuiz) {
        return new ImageQuizMemberResponse(imageQuiz.getTitle());
    }
}
