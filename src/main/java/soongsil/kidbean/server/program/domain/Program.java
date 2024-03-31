package soongsil.kidbean.server.program.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import soongsil.kidbean.server.global.vo.ImageInfo;
import soongsil.kidbean.server.global.vo.ImageInfo2;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.program.domain.type.ProgramCategory;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "program_id")
    private Long programId;

    @Column(name = "teacher_name", length = 10)
    private String teacherName;

    @Column(name = "title", length = 25)
    private String title;

    @Column(name = "place", length = 63)
    private String place;

    @Column(name = "phone_number", length = 100)
    private String phoneNumber;

    @Column(name = "content", length = 1000)
    private String content;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private ProgramCategory programCategory;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="imageUrl",
            column=@Column(name="teacherImageUrl")),
            @AttributeOverride(name="fileName",
            column=@Column(name="fileName")),
            @AttributeOverride(name="folderName",
            column=@Column(name="folderName"))
    })
    private ImageInfo2 teacherImageUrl;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="imageUrl",
                    column=@Column(name="programImageUrl")),
            @AttributeOverride(name="fileName",
                    column=@Column(name="fileName")),
            @AttributeOverride(name="folderName",
                    column=@Column(name="folderName"))
    })
    private ImageInfo2 programImageURl;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}
