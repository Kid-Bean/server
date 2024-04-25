package soongsil.kidbean.server.quiz.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import soongsil.kidbean.server.auth.dto.AuthUser;
import soongsil.kidbean.server.global.dto.ResponseTemplate;
import soongsil.kidbean.server.quiz.application.ImageQuizService;
import soongsil.kidbean.server.quiz.dto.request.QuizSolvedListRequest;
import soongsil.kidbean.server.quiz.dto.request.ImageQuizUpdateRequest;
import soongsil.kidbean.server.quiz.dto.request.ImageQuizUploadRequest;
import soongsil.kidbean.server.quiz.dto.response.ImageQuizMemberDetailResponse;
import soongsil.kidbean.server.quiz.dto.response.ImageQuizMemberResponse;
import soongsil.kidbean.server.quiz.dto.response.ImageQuizResponse;
import soongsil.kidbean.server.quiz.dto.response.ImageQuizSolveScoreResponse;

import java.util.List;

import static soongsil.kidbean.server.global.dto.ResponseTemplate.EMPTY_RESPONSE;

@Tag(name = "ImageQuiz", description = "ImageQuiz 관련 API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz/image")
public class ImageQuizController {

    private final ImageQuizService imageQuizService;

    @Operation(summary = "추가한 ImageQuiz 문제 상세 정보 가져오기", description = "ImageQuiz 상세 정보 가져오기")
    @GetMapping("/member/{quizId}")
    public ResponseEntity<ResponseTemplate<Object>> getImageQuizById(
            @AuthenticationPrincipal AuthUser user,
            @PathVariable Long quizId) {

        ImageQuizMemberDetailResponse response = imageQuizService.getImageQuizById(user.memberId(), quizId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }

    @Operation(summary = "추가한 ImageQuiz 문제 리스트 가져오기", description = "ImageQuiz 리스트 가져오기")
    @GetMapping("/member")
    public ResponseEntity<ResponseTemplate<Object>> getAllImageQuizByMember(
            @AuthenticationPrincipal AuthUser user) {

        List<ImageQuizMemberResponse> response =
                imageQuizService.getAllImageQuizByMember(user.memberId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }

    @Operation(summary = "ImageQuiz 문제 가져오기", description = "랜덤 ImageQuiz 가져오기")
    @GetMapping("/solve")
    public ResponseEntity<ResponseTemplate<Object>> getRandomImageQuiz(
            @AuthenticationPrincipal AuthUser user) {

        ImageQuizResponse imageQuizResponse =
                imageQuizService.selectRandomImageQuiz(user.memberId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(imageQuizResponse));
    }

    @Operation(summary = "ImageQuiz 문제 풀기", description = "푼 ImageQuiz 문제를 제출")
    @PostMapping("/solve")
    public ResponseEntity<ResponseTemplate<Object>> solveImageQuizzes(
            @AuthenticationPrincipal AuthUser user,
            @Valid @RequestBody QuizSolvedListRequest request) {

        ImageQuizSolveScoreResponse score = imageQuizService.solveImageQuizzes(
                request.quizSolvedRequestList(), user.memberId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(score));
    }

    @Operation(summary = "ImageQuiz 문제 등록하기", description = "ImageQuiz 등록하기")
    @PostMapping("/member")
    public ResponseEntity<ResponseTemplate<Object>> uploadImageQuiz(
            @AuthenticationPrincipal AuthUser user,
            @Valid @RequestPart ImageQuizUploadRequest imageQuizUploadRequest,
            @RequestPart MultipartFile s3Url) {

        imageQuizService.uploadImageQuiz(imageQuizUploadRequest, user.memberId(), s3Url);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }

    @Operation(summary = "ImageQuiz 문제 수정하기", description = "ImageQuiz 수정하기")
    @PutMapping("/member/{quizId}")
    public ResponseEntity<ResponseTemplate<Object>> updateImageQuiz(
            @AuthenticationPrincipal AuthUser user,
            @PathVariable Long quizId,
            @Valid @RequestPart ImageQuizUpdateRequest imageQuizUpdateRequest,
            @RequestPart MultipartFile s3Url) {

        imageQuizService.updateImageQuiz(imageQuizUpdateRequest, user.memberId(), quizId, s3Url);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }

    @Operation(summary = "ImageQuiz 문제 삭제하기", description = "ImageQuiz 삭제하기")
    @DeleteMapping("/member/{quizId}")
    public ResponseEntity<ResponseTemplate<Object>> deleteImageQuiz(
            @AuthenticationPrincipal AuthUser user,
            @PathVariable Long quizId) {

        imageQuizService.deleteImageQuiz(user.memberId(), quizId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }
}
