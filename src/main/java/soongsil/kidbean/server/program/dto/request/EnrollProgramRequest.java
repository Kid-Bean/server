package soongsil.kidbean.server.program.dto.request;

import jakarta.validation.constraints.NotNull;

import soongsil.kidbean.server.program.domain.type.ProgramCategory;

import java.util.List;


public record EnrollProgramRequest(

        @NotNull(message = "선생님 이름을 입력해주세요")
        String teacherName,
        @NotNull(message = "제목을 입력해주세요")
        String title,
        @NotNull(message = "장소를 입력해주세요")
        String place,
        @NotNull(message = "전화번호를 입력해주세요")
        String phoneNumber,
        @NotNull(message = "내용을 입력해주세요")
        String content,
        ProgramCategory programCategory,
        List<String> date
) {

}
