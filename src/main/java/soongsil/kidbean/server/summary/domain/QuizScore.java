package soongsil.kidbean.server.summary.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quizsolve.domain.type.Level;
import soongsil.kidbean.server.quizsolve.domain.type.QuizCategory;

@Table(name = "quiz_score", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"member_id", "category"})
})
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "score_id")
    private Long scoreId;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private QuizCategory quizCategory;

    @Column(name = "total_score")
    private Long totalScore;

    @Column(name = "quiz_count")
    private Long quizCount;

    @Builder
    public QuizScore(Member member, QuizCategory quizCategory, Long totalScore, Long quizCount) {
        this.member = member;
        this.quizCategory = quizCategory;
        this.totalScore = totalScore;
        this.quizCount = quizCount;
    }

    public static QuizScore makeInitQuizScore(Member member, QuizCategory quizCategory) {
        return new QuizScore(member, quizCategory, 0L, 0L);
    }

    public void updateScore(Level beforeLevel, Level afterLevel) {
        totalScore = totalScore - Level.getPoint(beforeLevel) + Level.getPoint(afterLevel);
    }

    public void addScore(Long score) {
        totalScore = totalScore + score;
        quizCount++;
    }
}
