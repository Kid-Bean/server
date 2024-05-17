package soongsil.kidbean.server.program.dto.request;

import jakarta.validation.constraints.NotNull;

import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.program.domain.Day;
import soongsil.kidbean.server.program.domain.Program;
import soongsil.kidbean.server.program.domain.type.Date;
import soongsil.kidbean.server.program.domain.type.ProgramCategory;

import java.util.List;
import soongsil.kidbean.server.program.domain.vo.DepartmentInfo;
import soongsil.kidbean.server.program.domain.vo.ProgramInfo;


public record EnrollProgramRequest(

        @NotNull(message = "제목을 입력해주세요")
        String programTitle,
        @NotNull(message = "글의 제목을 입력해주세요")
        String contentTitle,
        @NotNull(message = "내용을 입력해주세요")
        String content,
        ProgramCategory programCategory,
        List<String> date,
        @NotNull(message = "장소를 입력해주세요")
        String place,
        @NotNull(message = "관련 부서 이름을 입력해주세요")
        String departmentName,
        @NotNull(message = "부서 전화번호를 입력해주세요")
        String phoneNumber
) {
    public Program toEntity(Member member) {
        Program program = Program.builder()
                .programInfo(ProgramInfo.builder()
                        .programTitle(programTitle)
                        .contentTitle(contentTitle)
                        .content(content)
                        .programCategory(programCategory)
                        .place(place)
                        .build())
                .departmentInfo(DepartmentInfo.builder()
                        .departmentName(departmentName)
                        .phoneNumber(phoneNumber)
                        .build())
                .member(member)
                .build();

        date.stream()
                .map(day -> Day.builder()
                        .date(Date.getDayOfWeek(day))
                        .build())
                .forEach(program::addDay);

        return program;
    }

}
