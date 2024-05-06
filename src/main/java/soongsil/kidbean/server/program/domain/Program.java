package soongsil.kidbean.server.program.domain;


import jakarta.persistence.*;
import lombok.*;
import soongsil.kidbean.server.global.vo.S3Info;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.program.domain.type.Date;
import soongsil.kidbean.server.program.domain.type.ProgramCategory;

@Entity
@Getter
@Setter
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
            @AttributeOverride(name = "imageUrl", column = @Column(name = "teacher_image_url", length = 200)),
            @AttributeOverride(name = "fileName", column = @Column(name = "teacher_file_name", length = 200)),
            @AttributeOverride(name = "folderName", column = @Column(name = "teacher_folder_name", length = 100))
    })
    private S3Info teacherImageInfo;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "s3Url", column = @Column(name = "program_image_url", length = 200)),
            @AttributeOverride(name = "fileName", column = @Column(name = "program_file_name", length = 200)),
            @AttributeOverride(name = "folderName", column = @Column(name = "program_folder_name", length = 100))
    })
    private S3Info programImageInfo;

    @JoinColumn(name = "uploader_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder
    public Program(String teacherName,
                   String title,
                   String place,
                   String phoneNumber,
                   String content,
                   ProgramCategory programCategory,
                   S3Info teacherImageInfo,
                   S3Info programImageInfo,
                   Member member) {
        this.teacherName = teacherName;
        this.title = title;
        this.place = place;
        this.phoneNumber = phoneNumber;
        this.content = content;
        this.programCategory = programCategory;
        this.teacherImageInfo = teacherImageInfo;
        this.programImageInfo = programImageInfo;
        this.member = member;
    }

    public void setS3Info(S3Info programImageInfo, S3Info teacherImageInfo) {
        this.programImageInfo = programImageInfo;
        this.teacherImageInfo = teacherImageInfo;
    }


    @Builder
    public Program(String title, String content, S3Info programImageInfo, S3Info teacherImageInfo) {
        this.title = title;
        this.content = content;
        this.programImageInfo = programImageInfo;
        this.teacherImageInfo = teacherImageInfo;
    }

    public void setProgramInfo(String title,
                               String place,
                               String content,
                               S3Info teacherImageInfo,
                               S3Info programImageInfo,
                               String teacherName,
                               String phoneNumber){

        this.programImageInfo = programImageInfo;
        this.teacherImageInfo = teacherImageInfo;
        this.title = title;
        this.content = content;
        this.teacherName = teacherName;
        this.phoneNumber = phoneNumber;
        this.place = place;
    }

}
