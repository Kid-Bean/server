package soongsil.kidbean.server.quiz.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WordQuizWord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "word_id")
    private Long wordId;

    @Column(name = "content", length = 30)
    private String content;

    @JoinColumn(name = "quiz_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private WordQuiz quiz;

    @Builder
    public WordQuizWord(String content, WordQuiz quiz) {
        this.content = content;
        this.quiz = quiz;
    }
}
