package soongsil.kidbean.server.quiz.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soongsil.kidbean.server.quiz.application.SentenceQuizService;
import soongsil.kidbean.server.quiz.dto.response.SentenceQuizMemberDetailResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz/sentence")
public class SentenceQuizController {

    private final SentenceQuizService sentenceQuizService;

    @GetMapping("/member/{memberId}/{quizId}")
    public ResponseEntity<SentenceQuizMemberDetailResponse> getSentenceQuizById(@PathVariable Long memberId,
                                                                                @PathVariable Long quizId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(sentenceQuizService.getSentenceQuizById(memberId, quizId));
    }
}
