package soongsil.kidbean.server.quiz.presentation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soongsil.kidbean.server.quiz.application.ImageQuizService;
import soongsil.kidbean.server.quiz.dto.request.ImageQuizSolvedRequest;
import org.springframework.web.multipart.MultipartFile;
import soongsil.kidbean.server.quiz.dto.request.ImageQuizUpdateRequest;
import soongsil.kidbean.server.quiz.dto.request.ImageQuizUploadRequest;
import soongsil.kidbean.server.quiz.dto.response.ImageQuizMemberDetailResponse;
import soongsil.kidbean.server.quiz.dto.response.ImageQuizMemberResponse;
import soongsil.kidbean.server.quiz.dto.response.ImageQuizResponse;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz/image")
public class ImageQuizController {

    private final ImageQuizService imageQuizService;

    @GetMapping("/member/{memberId}/{quizId}")
    public ResponseEntity<ImageQuizMemberDetailResponse> getImageQuizById(@PathVariable Long memberId,
                                                                          @PathVariable Long quizId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(imageQuizService.getImageQuizById(memberId, quizId));
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<ImageQuizMemberResponse>> getAllImageQuizByMember(@PathVariable Long memberId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(imageQuizService.getAllImageQuizByMember(memberId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ImageQuizResponse> getRandomImageQuiz(@PathVariable Long userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(imageQuizService.selectRandomProblem(userId));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Long> solveImageQuizzes(@PathVariable Long userId,
                                                  @RequestBody List<ImageQuizSolvedRequest> request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(imageQuizService.solveImageQuizzes(request, userId));
    }

    @PostMapping("/member/{memberId}")
    public ResponseEntity<Void> uploadImageQuiz(@PathVariable Long memberId,
                                                @RequestPart ImageQuizUploadRequest imageQuizUploadRequest,
                                                @RequestPart(value = "image") MultipartFile image) throws IOException {
        imageQuizService.uploadImageQuiz(imageQuizUploadRequest, memberId, image);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PutMapping("/member/{memberId}/{quizId}")
    public ResponseEntity<Void> updateImageQuiz(@PathVariable Long memberId,
                                                @PathVariable Long quizId,
                                                @RequestPart ImageQuizUpdateRequest imageQuizUpdateRequest,
                                                @RequestPart(value = "image") MultipartFile image) throws IOException {
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
