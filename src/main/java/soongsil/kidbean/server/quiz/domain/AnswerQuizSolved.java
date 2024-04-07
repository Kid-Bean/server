package soongsil.kidbean.server.quiz.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import soongsil.kidbean.server.global.vo.S3Info;
import soongsil.kidbean.server.member.domain.Member;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerQuizSolved {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "solved_id")
    private Long solvedId;

    @Embedded
    private S3Info recordAnswer;

    @Column(name = "word_answer", length = 80)
    private String wordAnswer;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime solvedTime;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "word_quiz_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private WordQuiz wordQuiz;

    @JoinColumn(name = "answer_quiz_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private AnswerQuiz answerQuiz;

    @Builder
    public AnswerQuizSolved(S3Info recordAnswer, String wordAnswer, LocalDateTime solvedTime, Member member,
                            WordQuiz wordQuiz, AnswerQuiz answerQuiz) {
        this.recordAnswer = recordAnswer;
        this.wordAnswer = wordAnswer;
        this.solvedTime = solvedTime;
        this.member = member;
        this.wordQuiz = wordQuiz;
        this.answerQuiz = answerQuiz;
    }
}
