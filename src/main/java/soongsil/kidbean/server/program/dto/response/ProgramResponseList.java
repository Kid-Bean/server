package soongsil.kidbean.server.program.dto.response;

import lombok.Builder;
import java.util.List;

@Builder
public record ProgramResponseList(
        List<ProgramResponse> programResponseList
) {

    public static ProgramResponseList from(List<ProgramResponse> programList) {
        return new ProgramResponseList(programList);
    }
}
