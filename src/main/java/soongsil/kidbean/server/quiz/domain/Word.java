package soongsil.kidbean.server.quiz.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "word_id")
    private Long wordId;

    @Column(name = "content", length = 30)
    private String content;

    @JoinColumn(name = "quiz_id")
    @ManyToOne(fetch = FetchType.LAZY)
    SentenceQuiz quiz;

    @Builder
    public Word(String content, SentenceQuiz quiz) {
        this.content = content;
        this.quiz = quiz;
    }
}
