package soongsil.kidbean.server.program.dto.response;

import lombok.Builder;
import soongsil.kidbean.server.program.domain.Program;

import java.util.List;

@Builder
public record ProgramDetailResponse(
        Long programId,
        String programTitle,
        String contentTitle,
        String content,
        String departmentName,
        String place,
        String phoneNumber,
        String programS3Url,
        String departmentS3Url,
        List<String> date
) {

    public static ProgramDetailResponse of(Program program, List<String> dates) {

        return ProgramDetailResponse.builder()
                .programId(program.getProgramId())
                .place(program.getProgramInfo().getPlace())
                .programTitle(program.getProgramInfo().getProgramTitle())
                .contentTitle(program.getProgramInfo().getContentTitle())
                .content(program.getProgramInfo().getContent())
                .programS3Url(program.getProgramInfo().getProgramS3Info().getS3Url())
                .phoneNumber(program.getDepartmentInfo().getPhoneNumber())
                .departmentName(program.getDepartmentInfo().getDepartmentName())
                .departmentS3Url(program.getDepartmentInfo().getDepartmentS3Info().getS3Url())
                .date(dates)
                .build();
    }
}

