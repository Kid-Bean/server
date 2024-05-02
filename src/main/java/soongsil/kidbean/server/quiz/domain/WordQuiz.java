package soongsil.kidbean.server.quiz.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quiz.domain.type.Level;

import java.util.ArrayList;
import java.util.List;
import soongsil.kidbean.server.quiz.domain.type.QuizCategory;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WordQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private Long quizId;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private QuizCategory quizCategory;

    @Column(name = "title", length = 30)
    private String title;

    @Column(name = "answer", length = 20)
    private String answer;

    @Enumerated(EnumType.STRING)
    private Level level;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToMany(mappedBy = "wordQuiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Word> words = new ArrayList<>();

    @Builder
    public WordQuiz(QuizCategory quizCategory, String title, String answer, Level level, Member member,
                    List<Word> words) {
        this.quizCategory = quizCategory;
        this.title = title;
        this.answer = answer;
        this.level = level;
        this.member = member;
        this.words = words;
    }

    public void updateWordQuiz(String title, String answer) {
        this.title = title;
        this.answer = answer;
    }
}
