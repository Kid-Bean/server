package soongsil.kidbean.server.quiz.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    @GetMapping("/{memberId}")
    public ResponseEntity<ResponseTemplate<Object>> getRandomWordQuiz(@PathVariable Long memberId) {

        WordQuizResponse WordQuizResponse = wordQuizService.selectRandomWordQuiz(memberId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(WordQuizResponse));
    }

    @Operation(summary = "WordQuiz 문제 풀기", description = "푼 WordQuiz 문제를 제출")
    @PostMapping("/{memberId}")
    public ResponseEntity<ResponseTemplate<Object>> solveImageQuizzes(@PathVariable Long memberId,
                                                                      @Valid @RequestBody QuizSolvedListRequest request) {

        WordQuizSolveScoreResponse score = wordQuizService.solveWordQuizzes(request.quizSolvedRequestList(), memberId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(score));
    }

    @Operation(summary = "추가한 WordQuiz 문제 상세 정보 가져오기", description = "WordQuiz 상세 정보 가져오기")
    @GetMapping("/member/{memberId}/{quizId}")
    public ResponseEntity<ResponseTemplate<Object>> getWordQuizById(@PathVariable Long memberId,
                                                                    @PathVariable Long quizId) {

        WordQuizMemberDetailResponse response = wordQuizService.getWordQuizById(memberId, quizId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }

    @Operation(summary = "추가한 WordQuiz 문제 리스트 가져오기", description = "WordQuiz 리스트 가져오기")
    @GetMapping("/member/{memberId}")
    public ResponseEntity<ResponseTemplate<Object>> getAllWordQuizByMember(@PathVariable Long memberId) {

        List<WordQuizMemberResponse> response = wordQuizService.getAllWordQuizByMember(memberId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }

    @Operation(summary = "WordQuiz 등록하기", description = "WordQuiz 등록하기")
    @PostMapping("/member/{memberId}")
    public ResponseEntity<ResponseTemplate<Object>> uploadWordQuiz(@PathVariable Long memberId,
                                                                   @Valid @RequestBody WordQuizUploadRequest request) {

        wordQuizService.uploadWordQuiz(request, memberId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }

    @Operation(summary = "WordQuiz 문제 수정하기", description = "WordQuiz 수정하기")
    @PutMapping("/member/{memberId}/{quizId}")
    public ResponseEntity<ResponseTemplate<Object>> updateImageQuiz(@PathVariable Long memberId,
                                                                    @PathVariable Long quizId,
                                                                    @Valid @RequestBody WordQuizUpdateRequest request) {

        wordQuizService.updateWordQuiz(request, memberId, quizId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }

    @Operation(summary = "WordQuiz 문제 삭제하기", description = "WordQuiz 삭제하기")
    @DeleteMapping("/member/{memberId}/{quizId}")
    public ResponseEntity<ResponseTemplate<Object>> deleteImageQuiz(@PathVariable Long memberId,
                                                                    @PathVariable Long quizId) {

        wordQuizService.deleteWordQuiz(memberId, quizId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }
}
