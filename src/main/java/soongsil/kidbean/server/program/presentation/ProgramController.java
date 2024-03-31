package soongsil.kidbean.server.program.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import soongsil.kidbean.server.program.application.ProgramService;
import soongsil.kidbean.server.program.dto.ProgramRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProgramController {

    private final ProgramService programService;

    @GetMapping("/program")
    public ResponseEntity<ProgramRequest> getProgramList(@RequestParam Long programId) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(programService.getProgramsList(programId));
    }
}
