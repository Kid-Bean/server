package soongsil.kidbean.server.quiz.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import soongsil.kidbean.server.mypage.domain.Member;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageQuizSolved {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "solved_id")
    private Long solvedId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime solvedTime;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @Column(name = "answer", length = 30)
    private String answer;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    Member member;

    @JoinColumn(name = "image_quiz_id")
    @OneToOne(fetch = FetchType.LAZY)
    ImageQuiz imageQuiz;

    @Builder
    public ImageQuizSolved(LocalDateTime solvedTime, Boolean isCorrect, String answer, Member member, ImageQuiz imageQuiz) {
        this.solvedTime = solvedTime;
        this.isCorrect = isCorrect;
        this.answer = answer;
        this.member = member;
        this.imageQuiz = imageQuiz;
    }
}
