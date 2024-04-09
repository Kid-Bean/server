package soongsil.kidbean.server.program.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import soongsil.kidbean.server.program.application.ProgramService;
import soongsil.kidbean.server.program.domain.type.ProgramCategory;
import soongsil.kidbean.server.program.dto.response.ProgramListResponse;
import soongsil.kidbean.server.program.dto.response.ProgramDetailResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProgramController {

    private final ProgramService programService;

    @GetMapping("/programs/{programId}")
    public ResponseEntity<ProgramDetailResponse> getProgramInfo(@PathVariable Long programId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(programService.getProgramInfo(programId));
    }

    @GetMapping("/programs")
    public ResponseEntity<ProgramListResponse> getProgramListInfo(@RequestParam ProgramCategory category){
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(programService.getProgramListInfo(category));
    }
}
