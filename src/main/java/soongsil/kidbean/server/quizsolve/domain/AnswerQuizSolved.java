package soongsil.kidbean.server.quizsolve.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import soongsil.kidbean.server.answerquiz.domain.AnswerQuiz;
import soongsil.kidbean.server.global.domain.BaseTimeEntity;
import soongsil.kidbean.server.global.domain.S3Info;
import soongsil.kidbean.server.member.domain.Member;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerQuizSolved extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "solved_id")
    private Long solvedId;

    @Embedded
    private S3Info recordAnswer;

    @Column(name = "sentence_answer", length = 80)
    private String sentenceAnswer;

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
