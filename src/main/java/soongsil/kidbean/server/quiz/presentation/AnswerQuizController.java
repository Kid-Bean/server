package soongsil.kidbean.server.quiz.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import soongsil.kidbean.server.global.dto.ResponseTemplate;
import soongsil.kidbean.server.quiz.application.AnswerQuizService;
import soongsil.kidbean.server.quiz.dto.request.AnswerQuizSolvedRequest;
import soongsil.kidbean.server.quiz.dto.request.AnswerQuizUpdateRequest;
import soongsil.kidbean.server.quiz.dto.request.AnswerQuizUploadRequest;
import soongsil.kidbean.server.quiz.dto.response.AnswerQuizMemberDetailResponse;
import soongsil.kidbean.server.quiz.dto.response.AnswerQuizMemberResponse;
import soongsil.kidbean.server.quiz.dto.response.AnswerQuizResponse;
import soongsil.kidbean.server.quiz.dto.response.AnswerQuizSolveScoreResponse;

import java.util.List;

import static soongsil.kidbean.server.global.dto.ResponseTemplate.EMPTY_RESPONSE;

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

    @Operation(summary = "추가한 AnswerQuiz 문제 상세 정보 가져오기", description = "AnswerQuiz 상세 정보 가져오기")
    @GetMapping("/member/{memberId}/{quizId}")
    public ResponseEntity<ResponseTemplate<Object>> getAnswerQuizById(@PathVariable Long memberId,
                                                                    @PathVariable Long quizId) {

        AnswerQuizMemberDetailResponse response = answerQuizService.getAnswerQuizById(memberId, quizId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }

    @Operation(summary = "AnswerQuiz 등록하기", description = "AnswerQuiz 등록하기")
    @PostMapping("/member/{memberId}")
    public ResponseEntity<ResponseTemplate<Object>> uploadAnswerQuiz(@PathVariable Long memberId,
                                                                   @Valid @RequestBody AnswerQuizUploadRequest request) {

        answerQuizService.uploadAnswerQuiz(request, memberId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }

    @Operation(summary = "AnswerQuiz 문제 수정하기", description = "AnswerQuiz 수정하기")
    @PutMapping("/member/{memberId}/{quizId}")
    public ResponseEntity<ResponseTemplate<Object>> updateAnswerQuiz(@PathVariable Long memberId,
                                                                    @PathVariable Long quizId,
                                                                    @Valid @RequestBody AnswerQuizUpdateRequest request) {

        answerQuizService.updateAnswerQuiz(request, memberId, quizId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }

    @Operation(summary = "AnswerQuiz 문제 삭제하기", description = "AnswerQuiz 삭제하기")
    @DeleteMapping("/member/{memberId}/{quizId}")
    public ResponseEntity<ResponseTemplate<Object>> deleteAnswerQuiz(@PathVariable Long memberId,
                                                                    @PathVariable Long quizId) {

        answerQuizService.deleteAnswerQuiz(memberId, quizId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }
}
