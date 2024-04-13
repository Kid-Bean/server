package soongsil.kidbean.server.quiz.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import soongsil.kidbean.server.member.domain.Member;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WordQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private Long quizId;

    @Column(name = "title", length = 30)
    private String title;

    @Column(name = "answer", length = 20)
    private String answer;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToMany(mappedBy = "wordQuiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Word> words = new ArrayList<>();

    @Builder
    public WordQuiz(String title, String answer, Member member, List<Word> words) {
        this.title = title;
        this.answer = answer;
        this.member = member;
        this.words = words;
    }

    public void update(String title, String answer) {
        this.title = title;
        this.answer = answer;
    }
}
