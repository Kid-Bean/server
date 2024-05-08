package soongsil.kidbean.server.program.dto.response;

import lombok.Builder;
import soongsil.kidbean.server.program.domain.Program;

import java.util.List;

@Builder
public record ProgramDetailResponse(
        Long programId,
        String teacherName,
        String title,
        String titleInfo,
        String place,
        String phoneNumber,
        String content,
        String programImageUrl,
        String teacherImageUrl,
        List<String> date
) {

    public static ProgramDetailResponse of(Program program,List<String> dates) {

        return ProgramDetailResponse
                .builder()
                .programId(program.getProgramId())
                .teacherName(program.getTeacherName())
                .place(program.getPlace())
                .phoneNumber(program.getPhoneNumber())
                .title(program.getTitle())
                .titleInfo(program.getTitleInfo())
                .content(program.getContent())
                .programImageUrl(program.getProgramImageInfo().getS3Url())
                .teacherImageUrl(program.getTeacherImageInfo().getS3Url())
                .date(dates)
                .build();
    }
}

