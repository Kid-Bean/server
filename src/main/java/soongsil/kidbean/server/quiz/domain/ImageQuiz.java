package soongsil.kidbean.server.quiz.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quiz.domain.type.QuizCategory;
import soongsil.kidbean.server.quiz.domain.type.Level;
import soongsil.kidbean.server.global.vo.S3Info;

import java.util.ArrayList;
import java.util.List;

@Table(name = "image_quiz")
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private Long quizId;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private QuizCategory quizCategory;

    @Column(name = "answer", length = 20)
    private String answer;

    @Column(name = "title", length = 30)
    private String title;

    @Embedded
    private S3Info s3Info;

    @Column(name = "quiz_level")
    @Enumerated(EnumType.STRING)
    private Level level;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToMany(mappedBy = "imageQuiz", orphanRemoval = true)
    private List<QuizSolved> quizSolvedList = new ArrayList<>();

    @Builder
    public ImageQuiz(QuizCategory quizCategory, String answer, String title, S3Info s3Info, Level level,
                     Member member) {
        this.quizCategory = quizCategory;
        this.answer = answer;
        this.title = title;
        this.s3Info = s3Info;
        this.level = level;
        this.member = member;
    }

    public void setS3Info(S3Info s3Info) {
        this.s3Info = s3Info;
    }

    public void updateImageQuiz(String title, String answer, QuizCategory quizCategory, S3Info s3Info) {
        this.title = title;
        this.answer = answer;
        this.quizCategory = quizCategory;
        this.s3Info = s3Info;
    }


    public boolean isLevelUpdateNeed(Level newLevel) {
        return ((level == null) || (newLevel != level));
    }

    public void updateLevel(Level level) {
        this.level = level;
    }
}
