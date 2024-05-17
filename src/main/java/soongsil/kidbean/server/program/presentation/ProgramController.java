package soongsil.kidbean.server.program.presentation;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import soongsil.kidbean.server.auth.dto.AuthUser;
import soongsil.kidbean.server.global.dto.ResponseTemplate;
import soongsil.kidbean.server.program.application.ProgramService;
import soongsil.kidbean.server.program.domain.type.ProgramCategory;
import soongsil.kidbean.server.program.dto.request.EnrollProgramRequest;
import soongsil.kidbean.server.program.dto.request.UpdateProgramRequest;
import soongsil.kidbean.server.program.dto.response.ProgramDetailResponse;
import soongsil.kidbean.server.program.dto.response.ProgramResponseList;

import static soongsil.kidbean.server.global.dto.ResponseTemplate.EMPTY_RESPONSE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/programs")
public class ProgramController {

    private static final int PAGE_SIZE = 4;
    private final ProgramService programService;

    //목록조회 -> 페이징 진행
    @GetMapping
    public ResponseEntity<ResponseTemplate<Object>> getProgramListInfo(
            @RequestParam List<ProgramCategory> programCategoryList,
            @AuthenticationPrincipal AuthUser user,
            @RequestParam(defaultValue = "0") int page) {

        Pageable pageRequest = PageRequest.of(page, PAGE_SIZE);
        ProgramResponseList response = programService.getProgramInfoList(user.memberId(), programCategoryList,
                pageRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }

    //상세조회
    @GetMapping("/{programId}")
    public ResponseEntity<ResponseTemplate<Object>> getProgramInfo(
            @PathVariable Long programId) {

        ProgramDetailResponse response = programService.getProgramInfo(programId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }


    //삭제
    @DeleteMapping("/{programId}")
    public ResponseEntity<ResponseTemplate<Object>> deleteProgram(
            @PathVariable Long programId) {

        programService.deleteProgram(programId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }

    //수정
    @PutMapping
    public ResponseEntity<ResponseTemplate<Object>> editProgramInfo(
            @Valid @RequestPart UpdateProgramRequest updateProgramRequest,
            @RequestPart MultipartFile programImage,
            @RequestPart MultipartFile departmentImage) {

        programService.editProgramInfo(updateProgramRequest, programImage, departmentImage);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }

    //추가-등록
    @PostMapping
    public ResponseEntity<ResponseTemplate<Object>> createProgram(
            @AuthenticationPrincipal AuthUser user,
            @Valid @RequestPart EnrollProgramRequest enrollProgramRequest,
            @RequestPart MultipartFile programImage,
            @RequestPart MultipartFile departmentImage) {

        programService.createProgram(user.memberId(), enrollProgramRequest, programImage, departmentImage);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }
}