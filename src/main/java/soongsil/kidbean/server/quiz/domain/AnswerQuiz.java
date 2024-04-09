package soongsil.kidbean.server.quiz.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import soongsil.kidbean.server.member.domain.Member;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private Long quizId;

    @Column(name = "question", length = 40)
    private String question;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder
    public AnswerQuiz(String question, Member member) {
        this.question = question;
        this.member = member;
    }
}
