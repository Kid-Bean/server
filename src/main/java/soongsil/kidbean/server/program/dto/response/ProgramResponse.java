package soongsil.kidbean.server.program.dto.response;

import lombok.Builder;
import soongsil.kidbean.server.program.domain.Program;
import soongsil.kidbean.server.program.domain.type.ProgramCategory;

@Builder
public record ProgramResponse(
        Long programId,
        ProgramCategory programCategory,
        String teacherName,
        String place
) {
    public static ProgramResponse from(Program program) {
        return ProgramResponse
                .builder()
                .programId(program.getProgramId())
                .programCategory(program.getProgramCategory())
                .teacherName(program.getTeacherName())
                .place(program.getPlace())
                .build();
    }
}
