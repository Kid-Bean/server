package soongsil.kidbean.server.quiz.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import soongsil.kidbean.server.member.domain.Member;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecordQuizSolved {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "solved_id")
    private Long solvedId;

    @Column(name = "record_answer", length = 255)
    private String recordAnswer;

    @Column(name = "sentence_answer", length = 80)
    private String sentenceAnswer;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime solvedTime;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    Member member;

    @JoinColumn(name = "sentence_quiz_id")
    @ManyToOne(fetch = FetchType.LAZY)
    SentenceQuiz sentenceQuiz;

    @JoinColumn(name = "answer_quiz_id")
    @ManyToOne(fetch = FetchType.LAZY)
    AnswerQuiz answerQuiz;

    @Builder
    public RecordQuizSolved(String recordAnswer, String sentenceAnswer, LocalDateTime solvedTime, Member member, SentenceQuiz sentenceQuiz, AnswerQuiz answerQuiz) {
        this.recordAnswer = recordAnswer;
        this.sentenceAnswer = sentenceAnswer;
        this.solvedTime = solvedTime;
        this.member = member;
        this.sentenceQuiz = sentenceQuiz;
        this.answerQuiz = answerQuiz;
    }
}
