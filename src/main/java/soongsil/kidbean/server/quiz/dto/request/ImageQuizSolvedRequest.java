package soongsil.kidbean.server.quiz.dto.request;

import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quiz.domain.ImageQuiz;
import soongsil.kidbean.server.quiz.domain.ImageQuizSolved;

public record ImageQuizSolvedRequest(
        Long imageQuizId,
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
