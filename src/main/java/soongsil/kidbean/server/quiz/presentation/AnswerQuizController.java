package soongsil.kidbean.server.quiz.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import soongsil.kidbean.server.global.dto.ResponseTemplate;
import soongsil.kidbean.server.quiz.application.AnswerQuizService;
import soongsil.kidbean.server.quiz.dto.request.AnswerQuizSolvedRequest;
import soongsil.kidbean.server.quiz.dto.response.AnswerQuizMemberResponse;
import soongsil.kidbean.server.quiz.dto.response.AnswerQuizResponse;
import soongsil.kidbean.server.quiz.dto.response.AnswerQuizSolveScoreResponse;
import soongsil.kidbean.server.quiz.dto.response.WordQuizMemberResponse;

import java.util.List;

@Tag(name = "AnswerQuiz", description = "AnswerQuiz 관련 API 입니다.")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz/answer")
public class AnswerQuizController {

    private final AnswerQuizService answerQuizService;

    @Operation(summary = "AnswerQuiz 가져오기", description = "AnswerQuiz 가져오기")
    @GetMapping("/{memberId}")
    public ResponseEntity<ResponseTemplate<Object>> getRandomAnswerQuiz(@PathVariable Long memberId) {

        AnswerQuizResponse answerQuizResponse = answerQuizService.selectRandomAnswerQuiz(memberId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(answerQuizResponse));
    }

    @Operation(summary = "AnswerQuiz 문제 풀기", description = "AnswerQuiz 문제 풀기")
    @PostMapping("/{memberId}")
    public ResponseEntity<ResponseTemplate<Object>> solveAnswerQuiz(@PathVariable Long memberId,
                                                                    @RequestPart AnswerQuizSolvedRequest answerQuizSolvedRequest,
                                                                    @RequestPart MultipartFile record) {
        AnswerQuizSolveScoreResponse score = answerQuizService.submitAnswerQuiz(
                answerQuizSolvedRequest, record, memberId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(score));
    }

    @Operation(summary = "추가한 AnswerQuiz 문제 리스트 가져오기", description = "AnswerQuiz 리스트 가져오기")
    @GetMapping("/member/{memberId}")
    public ResponseEntity<ResponseTemplate<Object>> getAllAnswerQuizByMember(@PathVariable Long memberId) {

        List<AnswerQuizMemberResponse> response = answerQuizService.getAllAnswerQuizByMember(memberId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }
}
