package soongsil.kidbean.server.program.dto.response;

import lombok.Builder;
import soongsil.kidbean.server.program.domain.Program;
import soongsil.kidbean.server.program.domain.Star;
import soongsil.kidbean.server.program.domain.type.ProgramCategory;

@Builder
public record ProgramResponse(
        Long programId,
        ProgramCategory programCategory,
        String teacherName,
        String place,
        String teacherS3Url,
        String title,
        String starId
) {
    public static ProgramResponse of(Program program, Star star) {
        return ProgramResponse
                .builder()
                .programId(program.getProgramId())
                .programCategory(program.getProgramCategory())
                .teacherName(program.getTeacherName())
                .place(program.getPlace())
                .teacherS3Url(program.getTeacherS3Url().getS3Url())
                .title(program.getTitle())
                .starId(star.getStarId().toString())
                .build();
    }
}
