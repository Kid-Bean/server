package soongsil.kidbean.server.quiz.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UseWord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "use_word_id")
    private Long useWordId;

    @Column(name = "word_name", length = 30)
    private String wordName;

    @Column(name = "count")
    private Long count;

    @JoinColumn(name = "solved_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    AnswerQuizSolved answerQuizSolved;

    @Builder
    public UseWord(String wordName, Long count, AnswerQuizSolved answerQuizSolved) {
        this.wordName = wordName;
        this.count = count;
        this.answerQuizSolved = answerQuizSolved;
    }
}
