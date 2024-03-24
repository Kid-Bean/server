package soongsil.kidbean.server.quiz.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import soongsil.kidbean.server.mypage.domain.Member;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SentenceQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private Long quizId;

    @Column(name = "title", length = 30)
    private String title;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder
    public SentenceQuiz(String title, Member member) {
        this.title = title;
        this.member = member;
    }
}
