package soongsil.kidbean.server.answerquiz.domain;

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

    @Column(name = "title", length = 40)
    private String title;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder
    public AnswerQuiz(String question, String title, Member member) {
        this.question = question;
        this.title = title;
        this.member = member;
    }

    public void updateAnswerQuiz(String title, String question) {
        this.question = question;
        this.title = title;
    }
}
