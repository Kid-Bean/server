package soongsil.kidbean.server.program.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import soongsil.kidbean.server.auth.dto.AuthUser;
import soongsil.kidbean.server.global.dto.ResponseTemplate;
import soongsil.kidbean.server.program.application.ProgramService;
import soongsil.kidbean.server.program.domain.type.ProgramCategory;
import soongsil.kidbean.server.program.dto.request.EnrollProgramRequest;
import soongsil.kidbean.server.program.dto.response.ProgramListResponse;
import soongsil.kidbean.server.program.dto.response.ProgramDetailResponse;
import soongsil.kidbean.server.program.dto.response.ProgramResponse;
import soongsil.kidbean.server.program.dto.request.UpdateProgramRequest;

import static soongsil.kidbean.server.global.dto.ResponseTemplate.EMPTY_RESPONSE;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProgramController {

    private static final int PAGE_SIZE = 4;
    private final ProgramService programService;

    //목록조회 -> 페이징 진행
    @GetMapping("/programs")
    public ResponseEntity<ProgramListResponse> getProgramListInfo(@RequestParam ProgramCategory programcategory
            , @RequestParam(defaultValue = "0") int page) {
        Pageable pageRequest = PageRequest.of(page, PAGE_SIZE);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(programService.getProgramListInfo(programcategory, pageRequest));
    }

    //상세조회
    @GetMapping("/programs/{programId}")
    public ResponseEntity<ProgramDetailResponse> getProgramInfo(@PathVariable Long programId) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(programService.getProgramInfo(programId));
    }



    //삭제
    @DeleteMapping("/programs/{programId}")
    public ResponseEntity<ProgramResponse> deleteProgram(
            @AuthenticationPrincipal AuthUser user,
            @PathVariable Long programId) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(programService.deleteProgram(programId));
    }

    //수정
    @PatchMapping("/programs/{programId}")
    public ResponseEntity<ResponseTemplate<Object>> editProgramInfo(
            @AuthenticationPrincipal AuthUser user,
            @PathVariable Long programId,
            @Valid @RequestBody UpdateProgramRequest updateProgramRequest) {

        programService.editProgramInfo(programId, updateProgramRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);

    }

    //추가-등록
    @PostMapping("/program")
    public ResponseEntity<ResponseTemplate<Object>> createProgram(
            @AuthenticationPrincipal AuthUser user,
            @RequestBody ProgramCategory programCategory
            , @Valid @RequestBody EnrollProgramRequest enrollProgramRequest) {

        programService.createProgram(programCategory, enrollProgramRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }
}