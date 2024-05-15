package soongsil.kidbean.server.program.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import soongsil.kidbean.server.global.domain.S3Info;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.program.domain.vo.ProgramInfo;
import soongsil.kidbean.server.program.domain.vo.DepartmentInfo;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "program_id")
    private Long programId;

    @Embedded
    private ProgramInfo programInfo;

    @Embedded
    private DepartmentInfo departmentInfo;

    @JoinColumn(name = "uploader_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToMany(mappedBy = "program", orphanRemoval = true)
    private List<Day> dayList = new ArrayList<>();

    @Builder
    public Program(Long programId, ProgramInfo programInfo, DepartmentInfo departmentInfo, Member member) {
        this.programId = programId;
        this.programInfo = programInfo;
        this.departmentInfo = departmentInfo;
        this.member = member;
    }

    public void setS3Info(S3Info programS3Info, S3Info departmentS3Info) {
        this.programInfo.setProgramS3Info(programS3Info);
        this.departmentInfo.setDepartmentS3Info(departmentS3Info);
    }

    public S3Info getProgramS3Info() {
        return programInfo.getProgramS3Info();
    }

    public S3Info getDepartmentS3Info() {
        return departmentInfo.getDepartmentS3Info();
    }
}
