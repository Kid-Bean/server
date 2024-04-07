package soongsil.kidbean.server.quiz.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soongsil.kidbean.server.global.dto.ResponseTemplate;
import soongsil.kidbean.server.quiz.application.WordQuizService;
import soongsil.kidbean.server.quiz.dto.response.WordQuizResponse;
import soongsil.kidbean.server.quiz.dto.response.WordQuizMemberDetailResponse;

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

    @GetMapping("/member/{memberId}/{quizId}")
    public ResponseEntity<ResponseTemplate<Object>> getWordQuizById(@PathVariable Long memberId,
                                                                        @PathVariable Long quizId) {

        WordQuizMemberDetailResponse response = wordQuizService.getWordQuizById(memberId, quizId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }
}
