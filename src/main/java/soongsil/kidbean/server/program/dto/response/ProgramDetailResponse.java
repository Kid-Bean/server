package soongsil.kidbean.server.program.dto.response;

import lombok.Builder;
import soongsil.kidbean.server.program.domain.Program;


@Builder
public record ProgramDetailResponse(
        Long programId,
        String teacherName,
        String title,
        String place,
        String phoneNumber,
        String content,
        String programImageUrl,
        String teacherImageUrl
) {

    public static ProgramDetailResponse from(Program program) {

        return ProgramDetailResponse
                .builder()
                .programId(program.getProgramId())
                .teacherName(program.getTeacherName())
                .place(program.getPlace())
                .phoneNumber(program.getPhoneNumber())
                .title(program.getTitle())
                .content(program.getContent())
                .programImageUrl(program.getProgramImageInfo().getS3Url())
                .teacherImageUrl(program.getTeacherImageInfo().getS3Url())
                .build();
    }
}

