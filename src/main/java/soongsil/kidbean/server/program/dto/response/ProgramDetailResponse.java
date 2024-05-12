package soongsil.kidbean.server.program.dto.response;

import lombok.Builder;
import soongsil.kidbean.server.program.domain.Program;

import java.util.List;

@Builder
public record ProgramDetailResponse(
        Long programId,
        String teacherName,
        String title,
        String contentTitle,
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
                .contentTitle(program.getContentTitle())
                .content(program.getContent())
                .programImageUrl(program.getProgramS3Url().getS3Url())
                .teacherImageUrl(program.getTeacherS3Url().getS3Url())
                .date(dates)
                .build();
    }
}

