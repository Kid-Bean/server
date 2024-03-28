package soongsil.kidbean.server.mypage.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soongsil.kidbean.server.mypage.application.QuizSolvedService;
import soongsil.kidbean.server.mypage.dto.response.SolvedImageListResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("mypage/solved")
public class QuizSolvedController {

    private final QuizSolvedService quizSolvedService;

    @GetMapping("/image/{memberId}")
    public ResponseEntity<SolvedImageListResponse> findSolvedImage(@PathVariable Long memberId) {
        return ResponseEntity.ok(quizSolvedService.findSolvedImage(memberId));
    }
}
