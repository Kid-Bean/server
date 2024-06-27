package soongsil.kidbean.server.program.domain;

import jakarta.persistence.CascadeType;
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
import soongsil.kidbean.server.program.domain.type.Date;
import soongsil.kidbean.server.program.domain.type.ProgramCategory;
import soongsil.kidbean.server.program.dto.request.UpdateProgramRequest;

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

    @OneToMany(mappedBy = "program", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<OpenDay> openDayList = new ArrayList<>();

    @OneToMany(mappedBy = "program", cascade = CascadeType.REMOVE)
    private List<Star> starList = new ArrayList<>();

    @Builder
    public Program(ProgramInfo programInfo, DepartmentInfo departmentInfo, Member member) {
        this.programInfo = programInfo;
        this.departmentInfo = departmentInfo;
        this.member = member;
    }

    public void setS3Info(S3Info programS3Info, S3Info departmentS3Info) {
        this.programInfo.setProgramS3Info(programS3Info);
        this.departmentInfo.setDepartmentS3Info(departmentS3Info);
    }

    public void setProgramS3Info(S3Info programS3Info) {
        this.programInfo.setProgramS3Info(programS3Info);
    }

    public void setDepartmentS3Info(S3Info departmentS3Info) {
        this.departmentInfo.setDepartmentS3Info(departmentS3Info);
    }

    public S3Info getProgramS3Info() {
        return programInfo.getProgramS3Info();
    }

    public S3Info getDepartmentS3Info() {
        return departmentInfo.getDepartmentS3Info();
    }

    public ProgramCategory getProgramCategory() {
        return programInfo.getProgramCategory();
    }

    // 연관 관계 편의 메소드
    public void addDay(OpenDay openDay) {
        openDayList.add(openDay);
        openDay.setProgram(this);
    }

    public void updateProgram(UpdateProgramRequest updateProgramRequest) {
        programInfo.updateProgram(updateProgramRequest);
        departmentInfo.updateDepartment(updateProgramRequest);

        openDayList.removeIf(openDay -> !updateProgramRequest.date().contains(openDay.getDate().getDayOfWeek()));

        updateProgramRequest.date().stream()
                .filter(day -> openDayList.stream().noneMatch(d -> d.getDate().getDayOfWeek().equals(day)))
                .map(day -> OpenDay.builder()
                        .date(Date.getDayOfWeek(day))
                        .build())
                .forEach(this::addDay);
    }
}
