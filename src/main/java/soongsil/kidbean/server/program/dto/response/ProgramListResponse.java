package soongsil.kidbean.server.program.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ProgramListResponse(
        List<ProgramResponse> programResponseList
) {

    public static ProgramListResponse from(List<ProgramResponse> programList) {
        return new ProgramListResponse(programList);
    }
}
