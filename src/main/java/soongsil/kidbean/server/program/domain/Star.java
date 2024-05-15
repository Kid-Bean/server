package soongsil.kidbean.server.program.domain;

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
import soongsil.kidbean.server.member.domain.Member;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Star {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "star_id")
    private Long starId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @JoinColumn(name = "program_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Program program;

    @Builder
    public Star(Long starId, Member memberId, Program programId) {
        this.starId = starId;
        this.member = memberId;
        this.program = programId;
    }
}
