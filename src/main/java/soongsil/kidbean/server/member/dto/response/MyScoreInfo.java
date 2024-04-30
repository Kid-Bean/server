package soongsil.kidbean.server.member.dto.response;

import soongsil.kidbean.server.quiz.domain.type.QuizCategory;
import soongsil.kidbean.server.summary.domain.QuizScore;

public record MyScoreInfo(
        QuizCategory quizCategory,
        Long score,
        Long quizCount
) {
    public static MyScoreInfo from(QuizScore imageQuizScore) {
        return new MyScoreInfo(
                imageQuizScore.getQuizCategory(),
                imageQuizScore.getTotalScore(),
                imageQuizScore.getQuizCount()
        );
    }
}
