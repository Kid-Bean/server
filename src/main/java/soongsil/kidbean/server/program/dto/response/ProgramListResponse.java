package soongsil.kidbean.server.program.dto.response;

import lombok.Builder;
import org.springframework.data.domain.Page;
import soongsil.kidbean.server.program.domain.Program;

import java.util.List;

@Builder
public record ProgramListResponse(
        Page<ProgramResponse> programResponseList
) {

    public static ProgramListResponse from(Page<Program> programList) {

        return ProgramListResponse.builder()
                .programResponseList(
                        ( Page<ProgramResponse>) programList.stream()
                                .map(ProgramResponse::from)
                                .toList())
                .build();
    }
}
