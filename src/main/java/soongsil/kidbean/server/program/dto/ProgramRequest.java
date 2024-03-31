package soongsil.kidbean.server.program.dto;

import lombok.Builder;
import soongsil.kidbean.server.program.domain.Program;
@Builder
public record ProgramRequest(
        Long programId,
        String teacherName,
        String title,
        String place,
        String programUrl
) {
    public static ProgramRequest from(Program program) {
        return ProgramRequest
                .builder()
                .title(program.getTitle())
                .teacherName(program.getTeacherName())
                .build();
    }
}
