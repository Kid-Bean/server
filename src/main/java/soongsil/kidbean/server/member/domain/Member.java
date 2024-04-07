package soongsil.kidbean.server.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import soongsil.kidbean.server.member.domain.type.Role;
import soongsil.kidbean.server.member.domain.type.Gender;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "email", length = 25)
    private String email;

    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "score")
    private Long score;

    @Builder
    public Member(String email, String name, Gender gender, LocalDate birthDate, Role role, Long score) {
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.role = role;
        this.score = score;
    }
}
