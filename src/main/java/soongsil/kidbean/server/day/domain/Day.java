package soongsil.kidbean.server.day.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import soongsil.kidbean.server.day.domain.type.Date;
import soongsil.kidbean.server.program.domain.Program;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "day_id")
    private Long dayId;

    @Enumerated(EnumType.STRING)
    private Date date;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "program_id")
    private Program program;
}
