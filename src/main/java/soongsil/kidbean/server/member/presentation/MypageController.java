package soongsil.kidbean.server.member.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soongsil.kidbean.server.global.dto.ResponseTemplate;
import soongsil.kidbean.server.member.application.MypageService;

@RestController
@RequiredArgsConstructor
@RequestMapping("mypage/solved")
public class MypageController {

    private final MypageService mypageService;

    @GetMapping("/image/list/{memberId}")
    public ResponseEntity<ResponseTemplate<Object>> findSolvedImageList(
            @PathVariable(name = "memberId") Long memberId) {
        return ResponseEntity.ok(ResponseTemplate.from(mypageService.findSolvedImage(memberId)));
    }

    @GetMapping("/image/{solvedId}")
    public ResponseEntity<ResponseTemplate<Object>> findSolvedImageDetail(
            @PathVariable(name = "solvedId") Long solvedId) {
        return ResponseEntity.ok(ResponseTemplate.from(mypageService.solvedImageDetail(solvedId)));
    }

    @GetMapping("/word/list/{memberId}")
    public ResponseEntity<ResponseTemplate<Object>> findSolvedWordList(
            @PathVariable(name = "memberId") Long memberId) {
        return ResponseEntity.ok(ResponseTemplate.from(mypageService.findSolvedWord(memberId)));
    }

    @GetMapping("/word/{solvedId}")
    public ResponseEntity<ResponseTemplate<Object>> findSolvedWordDetail(
            @PathVariable(name = "solvedId") Long solvedId) {
        return ResponseEntity.ok(ResponseTemplate.from(mypageService.solvedWordDetail(solvedId)));
    }

    @GetMapping("/voice/list/{memberId}")
    public ResponseEntity<ResponseTemplate<Object>> findSolvedVoiceList(
            @PathVariable(name = "memberId") Long memberId
    ) {
        return ResponseEntity.ok(ResponseTemplate.from(mypageService.findSolvedAnswerQuiz(memberId)));
    }

    @GetMapping("/voice/{solvedId}")
    public ResponseEntity<ResponseTemplate<Object>> findSolvedVoiceDetail(
            @PathVariable(name = "solvedId") Long solvedId) {
        return ResponseEntity.ok(ResponseTemplate.from(mypageService.solvedAnswerQuizDetail(solvedId)));
    }

    @GetMapping("/image/result/{memberId}")
    public ResponseEntity<ResponseTemplate<Object>> findImageQuizScoreInfo(
            @PathVariable(name = "memberId") Long memberId) {
        return ResponseEntity.ok(ResponseTemplate.from(mypageService.findImageQuizScore(memberId)));
    }
}
