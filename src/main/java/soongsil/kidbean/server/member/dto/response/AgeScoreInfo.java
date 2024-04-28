package soongsil.kidbean.server.member.dto.response;

import soongsil.kidbean.server.quiz.domain.type.QuizCategory;
import soongsil.kidbean.server.summary.domain.AverageScore;

public record AgeScoreInfo(
        QuizCategory quizCategory,
        Long score,
        Long memberCount
) {

    public static AgeScoreInfo from(AverageScore averageScore) {
        return new AgeScoreInfo(
                averageScore.getQuizCategory(),
                averageScore.getTotalScore(),
                averageScore.getMemberCount()
        );
    }
}
