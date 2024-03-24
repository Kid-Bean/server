package soongsil.kidbean.server.quiz.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import soongsil.kidbean.server.mypage.domain.Member;
import soongsil.kidbean.server.quiz.domain.type.Category;
import soongsil.kidbean.server.quiz.domain.type.Level;

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
    private Category category;

    @Column(name = "answer", length = 20)
    private String answer;

    @Column(name = "image_url", length = 50)
    private String imageUrl;

    @Column(name = "title", length = 30)
    private String title;

    @Column(name = "level")
    @Enumerated(EnumType.STRING)
    private Level level;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder
    public ImageQuiz(Category category, String answer, String imageUrl, String title, Level level, Member member) {
        this.category = category;
        this.answer = answer;
        this.imageUrl = imageUrl;
        this.title = title;
        this.level = level;
        this.member = member;
    }
}
