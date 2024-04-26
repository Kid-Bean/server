package soongsil.kidbean.server.member.dto.response;

import soongsil.kidbean.server.quiz.domain.type.QuizCategory;
import soongsil.kidbean.server.summary.domain.AverageScore;
import soongsil.kidbean.server.summary.domain.ImageQuizScore;

public record MyScoreInfo(
        QuizCategory quizCategory,
        Long score,
        Long quizCount
) {
    public static MyScoreInfo from(ImageQuizScore imageQuizScore) {
        return new MyScoreInfo(
                imageQuizScore.getQuizCategory(),
                imageQuizScore.getTotalScore(),
                imageQuizScore.getQuizCount()
        );
    }
}
