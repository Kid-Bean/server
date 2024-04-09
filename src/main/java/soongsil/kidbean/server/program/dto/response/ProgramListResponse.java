package soongsil.kidbean.server.program.dto.response;

import lombok.Builder;
import soongsil.kidbean.server.program.domain.Program;

import java.util.List;

@Builder
public record ProgramListResponse(
        List<ProgramResponse> programResponseList
) {

    public static ProgramListResponse from(List<Program> programList) {

        return ProgramListResponse.builder()
                .programResponseList(
                        programList.stream()
                                .map(ProgramResponse::from)
                                .toList())
                .build();
    }
}
