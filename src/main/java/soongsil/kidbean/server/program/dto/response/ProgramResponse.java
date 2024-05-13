package soongsil.kidbean.server.program.dto.response;

import lombok.Builder;
import soongsil.kidbean.server.program.domain.Program;
import soongsil.kidbean.server.program.domain.type.ProgramCategory;

@Builder
public record ProgramResponse(
        Long programId,
        ProgramCategory programCategory,
        String departmentName,
        String place,
        String departmentS3Url,
        String programTitle,
        Boolean isStar
) {
    public static ProgramResponse of(Program program, Boolean isStar) {

        return ProgramResponse.builder()
                .programId(program.getProgramId())
                .programCategory(program.getProgramInfo().getProgramCategory())
                .place(program.getProgramInfo().getPlace())
                .programTitle(program.getProgramInfo().getProgramTitle())
                .departmentName(program.getDepartmentInfo().getDepartmentName())
                .departmentS3Url(program.getDepartmentInfo().getDepartmentS3Info().getS3Url())
                .isStar(isStar)
                .build();
    }
}
