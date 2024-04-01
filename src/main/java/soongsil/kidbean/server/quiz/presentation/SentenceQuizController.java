package soongsil.kidbean.server.quiz.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soongsil.kidbean.server.quiz.application.SentenceQuizService;
import soongsil.kidbean.server.quiz.dto.response.SentenceQuizResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz/sentence")
public class SentenceQuizController {

    private final SentenceQuizService sentenceQuizService;

    @GetMapping("/{memberId}")
    public ResponseEntity<SentenceQuizResponse> getRandomSentenceQuiz(@PathVariable Long memberId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(sentenceQuizService.selectRandomSentenceQuiz(memberId));
    }
}
