package soongsil.kidbean.server.quiz.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import soongsil.kidbean.server.global.vo.S3Info;
import soongsil.kidbean.server.member.domain.Member;

import java.time.LocalDateTime;

@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerQuizSolved {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "solved_id")
    private Long solvedId;

    @Embedded
    private S3Info recordAnswer;

    @Column(name = "sentence_answer", length = 80)
    private String sentenceAnswer;

    @LastModifiedDate
    private LocalDateTime solvedTime;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "answer_quiz_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private AnswerQuiz answerQuiz;

    @Builder
    public AnswerQuizSolved(S3Info recordAnswer, String sentenceAnswer, Member member, AnswerQuiz answerQuiz) {
        this.recordAnswer = recordAnswer;
        this.sentenceAnswer = sentenceAnswer;
        this.member = member;
        this.answerQuiz = answerQuiz;
    }
}
