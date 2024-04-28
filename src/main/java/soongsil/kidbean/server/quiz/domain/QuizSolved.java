package soongsil.kidbean.server.quiz.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.domain.type.Role;

@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizSolved {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "solved_id")
    private Long solvedId;

    @Column(name = "is_correct", length = 30)
    private Boolean isCorrect;

    @Column(name = "reply", length = 30)
    private String reply;

    @LastModifiedDate
    private LocalDateTime solvedTime;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "word_quiz_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private WordQuiz wordQuiz;

    @JoinColumn(name = "image_quiz_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ImageQuiz imageQuiz;

    @Builder
    public QuizSolved(Boolean isCorrect, String reply, LocalDateTime solvedTime, Member member, WordQuiz wordQuiz,
                      ImageQuiz imageQuiz) {
        this.isCorrect = isCorrect;
        this.reply = reply;
        this.solvedTime = solvedTime;
        this.member = member;
        this.wordQuiz = wordQuiz;
        this.imageQuiz = imageQuiz;
    }

    public void setAnswerIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public boolean isImageQuizMadeByAdmin() {
        return imageQuiz.getMember().getRole().equals(Role.ADMIN);
    }
}
