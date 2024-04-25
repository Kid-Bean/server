package soongsil.kidbean.server.quiz.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soongsil.kidbean.server.auth.dto.AuthUser;
import soongsil.kidbean.server.global.dto.ResponseTemplate;
import soongsil.kidbean.server.quiz.application.WordQuizService;
import soongsil.kidbean.server.quiz.dto.request.QuizSolvedListRequest;
import soongsil.kidbean.server.quiz.dto.request.WordQuizUpdateRequest;
import soongsil.kidbean.server.quiz.dto.request.WordQuizUploadRequest;
import soongsil.kidbean.server.quiz.dto.response.WordQuizMemberDetailResponse;
import soongsil.kidbean.server.quiz.dto.response.WordQuizMemberResponse;
import soongsil.kidbean.server.quiz.dto.response.WordQuizResponse;

import java.util.List;
import soongsil.kidbean.server.quiz.dto.response.WordQuizSolveScoreResponse;

import static soongsil.kidbean.server.global.dto.ResponseTemplate.EMPTY_RESPONSE;

@Tag(name = "WordQuiz", description = "WordQuiz 관련 API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz/word")
public class WordQuizController {

    private final WordQuizService wordQuizService;

    @Operation(summary = "WordQuiz 문제 불러오기", description = "WordQuiz 문제 불러오기")
    @GetMapping
    public ResponseEntity<ResponseTemplate<Object>> getRandomWordQuiz(@AuthenticationPrincipal AuthUser user) {

        WordQuizResponse WordQuizResponse = wordQuizService.selectRandomWordQuiz(user.getMemberId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(WordQuizResponse));
    }

    @Operation(summary = "WordQuiz 문제 풀기", description = "푼 WordQuiz 문제를 제출")
    @PostMapping
    public ResponseEntity<ResponseTemplate<Object>> solveImageQuizzes(
            @AuthenticationPrincipal AuthUser user,
            @Valid @RequestBody QuizSolvedListRequest request) {

        WordQuizSolveScoreResponse score =
                wordQuizService.solveWordQuizzes(request.quizSolvedRequestList(), user.getMemberId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(score));
    }

    @Operation(summary = "추가한 WordQuiz 문제 상세 정보 가져오기", description = "WordQuiz 상세 정보 가져오기")
    @GetMapping("/member/{quizId}")
    public ResponseEntity<ResponseTemplate<Object>> getWordQuizById(
            @AuthenticationPrincipal AuthUser user,
            @PathVariable Long quizId) {

        WordQuizMemberDetailResponse response = wordQuizService.getWordQuizById(user.getMemberId(), quizId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }

    @Operation(summary = "추가한 WordQuiz 문제 리스트 가져오기", description = "WordQuiz 리스트 가져오기")
    @GetMapping("/member")
    public ResponseEntity<ResponseTemplate<Object>> getAllWordQuizByMember(@AuthenticationPrincipal AuthUser user) {

        List<WordQuizMemberResponse> response = wordQuizService.getAllWordQuizByMember(user.getMemberId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }

    @Operation(summary = "WordQuiz 등록하기", description = "WordQuiz 등록하기")
    @PostMapping("/member")
    public ResponseEntity<ResponseTemplate<Object>> uploadWordQuiz(
            @AuthenticationPrincipal AuthUser user,
            @Valid @RequestBody WordQuizUploadRequest request) {

        wordQuizService.uploadWordQuiz(request, user.getMemberId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }

    @Operation(summary = "WordQuiz 문제 수정하기", description = "WordQuiz 수정하기")
    @PutMapping("/member/{quizId}")
    public ResponseEntity<ResponseTemplate<Object>> updateImageQuiz(
            @AuthenticationPrincipal AuthUser user,
            @PathVariable Long quizId,
            @Valid @RequestBody WordQuizUpdateRequest request) {

        wordQuizService.updateWordQuiz(request, user.getMemberId(), quizId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }

    @Operation(summary = "WordQuiz 문제 삭제하기", description = "WordQuiz 삭제하기")
    @DeleteMapping("/member/{quizId}")
    public ResponseEntity<ResponseTemplate<Object>> deleteImageQuiz(
            @AuthenticationPrincipal AuthUser user,
            @PathVariable Long quizId) {

        wordQuizService.deleteWordQuiz(user.getMemberId(), quizId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }
}
