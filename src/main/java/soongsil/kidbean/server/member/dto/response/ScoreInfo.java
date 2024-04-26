package soongsil.kidbean.server.member.dto.response;

import soongsil.kidbean.server.quiz.domain.type.QuizCategory;
import soongsil.kidbean.server.summary.domain.AverageScore;
import soongsil.kidbean.server.summary.domain.ImageQuizScore;

public record ScoreInfo(
        QuizCategory quizCategory,
        Long score,
        Long quizCount
) {
    public static ScoreInfo byImageQuizScore(ImageQuizScore imageQuizScore) {
        return new ScoreInfo(
                imageQuizScore.getQuizCategory(),
                imageQuizScore.getTotalScore(),
                imageQuizScore.getQuizCount()
        );
    }

    public static ScoreInfo byAverageScore(AverageScore averageScore) {
        return new ScoreInfo(
                averageScore.getQuizCategory(),
                averageScore.getTotalScore(),
                averageScore.getMemberCount()
        );
    }
}
