package soongsil.kidbean.server.program.dto.request;

import jakarta.validation.constraints.NotNull;
import soongsil.kidbean.server.global.vo.S3Info;

public record UpdateProgramRequest(

        @NotNull (message = "선생님 이름을 입력해주세요")
        String teacherName,
        @NotNull (message = "제목을 입력해주세요")
        String title,
        @NotNull (message = "장소를 입력해주세요")
        String place,
        @NotNull (message = "전화번호를 입력해주세요")
        String phoneNumber,
        @NotNull (message = "내용을 입력해주세요")
        String content,
        S3Info programS3Url,
        S3Info teacherS3Url
        
) {
}
