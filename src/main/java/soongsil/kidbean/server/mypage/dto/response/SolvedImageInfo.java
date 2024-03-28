package soongsil.kidbean.server.mypage.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import soongsil.kidbean.server.quiz.domain.ImageQuizSolved;

public record SolvedImageInfo(
        Long solvedId,
        LocalDateTime solvedTime
) {
    public static SolvedImageInfo from(ImageQuizSolved imageQuizSolved) {
        return new SolvedImageInfo(
                imageQuizSolved.getSolvedId(),
                imageQuizSolved.getSolvedTime()
        );
    }
}
