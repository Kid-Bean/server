package soongsil.kidbean.server.quizsolve.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import soongsil.kidbean.server.global.domain.BaseTimeEntity;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.domain.type.Role;
import soongsil.kidbean.server.imagequiz.domain.ImageQuiz;
import soongsil.kidbean.server.wordquiz.domain.WordQuiz;
import soongsil.kidbean.server.quizsolve.domain.type.QuizCategory;

import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizSolved extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "solved_id")
    private Long solvedId;

    @Column(name = "is_correct", length = 30)
    private Boolean isCorrect;

    @Column(name = "reply", length = 30)
    private String reply;

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
    public QuizSolved(Boolean isCorrect, String reply, Member member, WordQuiz wordQuiz,
                      ImageQuiz imageQuiz) {
        this.isCorrect = isCorrect;
        this.reply = reply;
        this.member = member;
        this.wordQuiz = wordQuiz;
        this.imageQuiz = imageQuiz;
    }

    public boolean isImageQuizMadeByAdmin() {
        return imageQuiz.getMember().getRole().equals(Role.ADMIN);
    }

    public Boolean isImageQuiz() {
        return this.getImageQuiz() == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QuizSolved that = (QuizSolved) o;
        return Objects.equals(member, that.member) &&
                (Objects.equals(imageQuiz, that.imageQuiz) || Objects.equals(wordQuiz, that.wordQuiz));
    }

    @Override
    public int hashCode() {
        return Objects.hash(member, imageQuiz, wordQuiz);
    }

    public QuizCategory getQuizCategory() {
        if (null != imageQuiz) {
            return imageQuiz.getQuizCategory();
        } else {
            return wordQuiz.getQuizCategory();
        }
    }
}
