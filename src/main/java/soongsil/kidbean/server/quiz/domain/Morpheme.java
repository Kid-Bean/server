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
public class Morpheme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "morpheme_id")
    private Long morphemeId;

    @Column(name = "morpheme", length = 30)
    private String morpheme;

    @Column(name = "type", length = 10)
    private String type;

    @JoinColumn(name = "solved_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    AnswerQuizSolved answerQuizSolved;

    @Builder
    public Morpheme(String morpheme, String type, AnswerQuizSolved answerQuizSolved) {
        this.morpheme = morpheme;
        this.type = type;
        this.answerQuizSolved = answerQuizSolved;
    }
}
