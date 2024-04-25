package soongsil.kidbean.server.quiz.presentation;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import soongsil.kidbean.server.auth.dto.AuthUser;
import soongsil.kidbean.server.global.dto.ResponseTemplate;
import soongsil.kidbean.server.quiz.application.AnswerQuizService;
import soongsil.kidbean.server.quiz.dto.request.AnswerQuizSolvedRequest;
import soongsil.kidbean.server.quiz.dto.response.AnswerQuizResponse;
import soongsil.kidbean.server.quiz.dto.response.AnswerQuizSolveScoreResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz/answer")
public class AnswerQuizController {

    private final AnswerQuizService answerQuizService;

    @Operation(summary = "AnswerQuiz 가져오기", description = "AnswerQuiz 가져오기")
    @GetMapping
    public ResponseEntity<ResponseTemplate<Object>> getRandomAnswerQuiz(
            @AuthenticationPrincipal AuthUser user) {

        AnswerQuizResponse answerQuizResponse = answerQuizService.selectRandomAnswerQuiz(user.memberId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(answerQuizResponse));
    }

    @Operation(summary = "AnswerQuiz 문제 풀기", description = "AnswerQuiz 문제 풀기")
    @PostMapping
    public ResponseEntity<ResponseTemplate<Object>> solveAnswerQuiz(
            @AuthenticationPrincipal AuthUser user,
            @RequestPart AnswerQuizSolvedRequest answerQuizSolvedRequest,
            @RequestPart MultipartFile record) {
        AnswerQuizSolveScoreResponse score = answerQuizService.submitAnswerQuiz(
                answerQuizSolvedRequest, record, user.memberId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(score));
    }
}
