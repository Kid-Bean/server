package soongsil.kidbean.server.quiz.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import soongsil.kidbean.server.member.domain.Member;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageQuizSolved {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "solved_id")
    private Long solvedId;

    @LastModifiedDate
    private LocalDateTime solvedTime;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @Column(name = "answer", length = 30)
    private String answer;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "image_quiz_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ImageQuiz imageQuiz;

    @Builder
    public ImageQuizSolved(Boolean isCorrect, String answer, Member member, ImageQuiz imageQuiz) {
        this.isCorrect = isCorrect;
        this.answer = answer;
        this.member = member;
        this.imageQuiz = imageQuiz;
    }

    public void setAnswerIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }
}
