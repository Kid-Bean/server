package soongsil.kidbean.server.quiz.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import soongsil.kidbean.server.quiz.application.ImageQuizService;
import soongsil.kidbean.server.quiz.dto.request.ImageQuizUpdateRequest;
import soongsil.kidbean.server.quiz.dto.request.ImageQuizUploadRequest;
import soongsil.kidbean.server.quiz.dto.response.ImageQuizMemberDetailResponse;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("quiz/image")
public class ImageQuizController {

    private final ImageQuizService imageQuizService;

    @GetMapping("/{memberId}/{quizId}")
    public ResponseEntity<ImageQuizMemberDetailResponse> getImageQuizById(@PathVariable Long memberId,
                                                                          @PathVariable Long quizId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(imageQuizService.getImageQuizById(memberId, quizId));
    }

    @PostMapping("/{memberId}")
    public ResponseEntity<Void> uploadImageQuiz(@PathVariable Long memberId,
                                                @RequestPart ImageQuizUploadRequest imageQuizUploadRequest,
                                                @RequestPart(value = "image") MultipartFile image) throws IOException {
        imageQuizService.uploadImageQuiz(imageQuizUploadRequest, memberId, image);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PutMapping("/{memberId}/{quizId}")
    public ResponseEntity<Void> updateImageQuiz(@PathVariable Long memberId,
                                                @PathVariable Long quizId,
                                                @RequestPart ImageQuizUpdateRequest imageQuizUpdateRequest,
                                                @RequestPart(value = "image") MultipartFile image) throws IOException {
        imageQuizService.updateImageQuiz(imageQuizUpdateRequest, memberId, quizId, image);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
