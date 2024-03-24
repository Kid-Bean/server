package soongsil.kidbean.server.program.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.program.domain.type.Category;
@Entity
@Table(name = "program")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "program_id")
    private Long programId;

    @NotNull
    @Column(length = 10)
    private String teacherName;

    @NotNull
    @Column(length =25)
    private String title;

    @NotNull
    @Column(length =63)
    private String place;

    @Column(length =100)
    private String phoneNumber;

    @Column(length =1000)
    private String content;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(length =50)
    private String teacherImageUrl;

    @Column(length =50)
    private String programImageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

}
