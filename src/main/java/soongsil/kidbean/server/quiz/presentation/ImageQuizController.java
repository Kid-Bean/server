package soongsil.kidbean.server.quiz.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soongsil.kidbean.server.global.dto.ResponseTemplate;
import soongsil.kidbean.server.quiz.application.ImageQuizService;
import soongsil.kidbean.server.quiz.dto.request.ImageQuizSolvedListRequest;
import soongsil.kidbean.server.quiz.dto.request.ImageQuizSolvedRequest;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import soongsil.kidbean.server.quiz.dto.request.ImageQuizUpdateRequest;
import soongsil.kidbean.server.quiz.dto.request.ImageQuizUploadRequest;
import soongsil.kidbean.server.quiz.dto.response.ImageQuizMemberDetailResponse;
import soongsil.kidbean.server.quiz.dto.response.ImageQuizMemberResponse;
import soongsil.kidbean.server.quiz.dto.response.ImageQuizResponse;
import soongsil.kidbean.server.quiz.dto.response.ImageQuizSolveScoreResponse;

@Tag(name = "ImageQuiz", description = "ImageQuiz 관련 API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz/image")
public class ImageQuizController {

    private final ImageQuizService imageQuizService;

    @GetMapping("/member/{memberId}/{quizId}")
    public ResponseEntity<ResponseTemplate<Object>> getImageQuizById(@PathVariable Long memberId,
                                                                          @PathVariable Long quizId) {

        ImageQuizMemberDetailResponse response = imageQuizService.getImageQuizById(memberId, quizId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<ResponseTemplate<Object>> getAllImageQuizByMember(@PathVariable Long memberId) {

        List<ImageQuizMemberResponse> response = imageQuizService.getAllImageQuizByMember(memberId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }

    @Operation(summary = "ImageQuiz 문제 가져오기", description = "랜덤 ImageQuiz 가져오기")
    @GetMapping("/{memberId}")
    public ResponseEntity<ResponseTemplate<Object>> getRandomImageQuiz(@PathVariable Long memberId) {

        ImageQuizResponse imageQuizResponse = imageQuizService.selectRandomImageQuiz(memberId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(imageQuizResponse));
    }

    @Operation(summary = "ImageQuiz 문제 풀기", description = "푼 ImageQuiz 문제를 제출")
    @PostMapping("/{userId}")
    public ResponseEntity<ResponseTemplate<Object>> solveImageQuizzes(@PathVariable Long userId,
                                                                      @Valid @RequestBody ImageQuizSolvedListRequest request) {

        ImageQuizSolveScoreResponse score = imageQuizService.solveImageQuizzes(request.imageQuizSolvedRequestList(),
                userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(score));
    }

    @PostMapping("/member/{memberId}")
    public ResponseEntity<Void> uploadImageQuiz(@PathVariable Long memberId,
                                                @RequestPart ImageQuizUploadRequest imageQuizUploadRequest,
                                                @RequestPart(value = "image") MultipartFile image) {
        imageQuizService.uploadImageQuiz(imageQuizUploadRequest, memberId, image);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PutMapping("/member/{memberId}/{quizId}")
    public ResponseEntity<Void> updateImageQuiz(@PathVariable Long memberId,
                                                @PathVariable Long quizId,
                                                @RequestPart ImageQuizUpdateRequest imageQuizUpdateRequest,
                                                @RequestPart MultipartFile image) {
        imageQuizService.updateImageQuiz(imageQuizUpdateRequest, memberId, quizId, image);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/member/{memberId}/{quizId}")
    public ResponseEntity<Void> deleteImageQuiz(@PathVariable Long memberId,
                                                @PathVariable Long quizId) {
        imageQuizService.deleteImageQuiz(memberId, quizId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
