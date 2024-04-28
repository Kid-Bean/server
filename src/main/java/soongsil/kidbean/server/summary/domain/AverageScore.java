package soongsil.kidbean.server.summary.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import soongsil.kidbean.server.quiz.domain.type.QuizCategory;
import soongsil.kidbean.server.summary.domain.type.AgeGroup;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AverageScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "average_score_id")
    private Long averageScoreId;

    @Column(name = "age_group")
    @Enumerated(EnumType.STRING)
    private AgeGroup ageGroup;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private QuizCategory quizCategory;

    @Column(name = "total_score")
    private Long totalScore;

    @Column(name = "member_count")
    private Long memberCount;

    @Builder
    public AverageScore(AgeGroup ageGroup, Long sum, long memberCount, QuizCategory category) {
        this.ageGroup = ageGroup;
        this.totalScore = sum;
        this.memberCount = memberCount;
        this.quizCategory = category;
    }

    public void updateScoreAndCount(long sum, long size) {
        totalScore = sum;
        memberCount = size;
    }
}
