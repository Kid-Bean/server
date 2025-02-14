package soongsil.kidbean.server.answerquiz.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quizsolve.domain.type.Level;
import soongsil.kidbean.server.quizsolve.domain.type.QuizCategory;

@Table(name = "answer_quiz", indexes = {
        @Index(name = "idx_answer_quiz", columnList = "question, title, member_id, is_default")
})
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

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private QuizCategory quizCategory;

    @Column(name = "quiz_level")
    @Enumerated(EnumType.STRING)
    private Level level;

    @Column(name = "is_default")
    private Boolean isDefault;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder
    public AnswerQuiz(String question, String title, Member member, Boolean isDefault, QuizCategory quizCategory, Level level) {
        this.question = question;
        this.title = title;
        this.quizCategory = quizCategory;
        this.level = level;
        this.isDefault = isDefault;
        this.member = member;
    }

    public void updateAnswerQuiz(String title, String question) {
        this.question = question;
        this.title = title;
    }
}
