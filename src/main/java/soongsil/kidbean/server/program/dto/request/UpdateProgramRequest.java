package soongsil.kidbean.server.program.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record UpdateProgramRequest(

        Long programId,
        @NotNull(message = "프로그램 제목을 입력해주세요")
        String programTitle,

        String contentTitle,

        List<String> date,

        @NotNull(message = "관련 부서 이름을 입력해주세요")
        String departmentName,

        @NotNull(message = "장소를 입력해주세요")
        String place,

        @NotNull(message = "부서 전화번호를 입력해주세요")
        String phoneNumber,

        @NotNull(message = "내용을 입력해주세요")
        String content
) {

}
