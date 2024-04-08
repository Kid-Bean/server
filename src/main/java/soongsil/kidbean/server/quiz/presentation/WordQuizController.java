package soongsil.kidbean.server.quiz.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soongsil.kidbean.server.global.dto.ResponseTemplate;
import soongsil.kidbean.server.quiz.application.WordQuizService;
import soongsil.kidbean.server.quiz.dto.response.WordQuizMemberResponse;
import soongsil.kidbean.server.quiz.dto.response.WordQuizResponse;
import soongsil.kidbean.server.quiz.dto.response.WordQuizMemberDetailResponse;

import java.util.List;

@Tag(name = "WordQuiz", description = "WordQuiz 관련 API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz/word")
public class WordQuizController {

    private final WordQuizService wordQuizService;

    @GetMapping("/{memberId}")
    public ResponseEntity<ResponseTemplate<Object>> getRandomWordQuiz(@PathVariable Long memberId) {

        WordQuizResponse WordQuizResponse = wordQuizService.selectRandomWordQuiz(memberId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(WordQuizResponse));
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
}
