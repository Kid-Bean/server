package soongsil.kidbean.server.quiz.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Analysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "analysis_id")
    private Long analysisId;

    @JoinColumn(name = "solved_id")
    @OneToOne(fetch = FetchType.LAZY)
    private RecordQuizSolved solvedId;

    @Builder
    private Analysis(RecordQuizSolved solvedId) {
        this.solvedId = solvedId;
    }
}
