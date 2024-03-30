package soongsil.kidbean.server.quiz.presentation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soongsil.kidbean.server.quiz.application.ImageQuizService;
import soongsil.kidbean.server.quiz.dto.request.ImageQuizSolvedRequest;
import soongsil.kidbean.server.quiz.dto.response.ImageQuizMemberDetailResponse;
import soongsil.kidbean.server.quiz.dto.response.ImageQuizResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz/image")
public class ImageQuizController {

    private final ImageQuizService imageQuizService;

    @GetMapping("/{memberId}/{quizId}")
    public ResponseEntity<ImageQuizMemberDetailResponse> getImageQuizById(@PathVariable Long memberId,
                                                                          @PathVariable Long quizId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(imageQuizService.getImageQuizById(memberId, quizId));
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

}
