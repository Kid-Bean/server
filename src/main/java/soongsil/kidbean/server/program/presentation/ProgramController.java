package soongsil.kidbean.server.program.presentation;

import jdk.jfr.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soongsil.kidbean.server.program.application.ProgramService;
import soongsil.kidbean.server.program.domain.Program;
import soongsil.kidbean.server.program.domain.type.ProgramCategory;
import soongsil.kidbean.server.program.dto.response.ProgramListResponse;
import soongsil.kidbean.server.program.dto.response.ProgramDetailResponse;
import soongsil.kidbean.server.program.dto.response.ProgramResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProgramController {

    private final ProgramService programService;

    //상세조회
    @GetMapping("/programs/{programId}")
    public ResponseEntity<ProgramDetailResponse> getProgramInfo(@PathVariable Long programId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(programService.getProgramInfo(programId));
    }

    //목록조회
    @GetMapping("/programs")
    public ResponseEntity<ProgramListResponse> getProgramListInfo(@RequestParam ProgramCategory category){
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(programService.getProgramListInfo(category));
    }


    //수정
    @PatchMapping("/programs")
    public ResponseEntity<ProgramDetailResponse> editProgramInfo(Long programId, Program editprogram){
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(programService.editProgramInfo(programId,editprogram));

    }

    //삭제
    @DeleteMapping("/programs/{programId}")
    public ResponseEntity<ProgramResponse> deleteProgram(@PathVariable Long programId){

        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(programService.deleteProgram(programId));
    }

    @PostMapping("/program?category={category}")
    public ResponseEntity<ProgramDetailResponse> createProgram(@RequestBody Category category , @PathVariable Program program){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(programService.createProgram(category,program));
    }
}

