package soongsil.kidbean.server.quiz.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soongsil.kidbean.server.global.dto.ResponseTemplate;
import soongsil.kidbean.server.quiz.application.AnswerQuizService;
import soongsil.kidbean.server.quiz.dto.response.AnswerQuizResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz/answer")
public class AnswerQuizController {

    private final AnswerQuizService answerQuizService;

    @GetMapping("/{memberId}")
    public ResponseEntity<ResponseTemplate<Object>> getRandomAnswerQuiz(@PathVariable Long memberId) {

        AnswerQuizResponse answerQuizResponse = answerQuizService.selectRandomAnswerQuiz(memberId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(answerQuizResponse));
    }
}
