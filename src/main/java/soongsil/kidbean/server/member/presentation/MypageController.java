package soongsil.kidbean.server.member.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import soongsil.kidbean.server.auth.dto.AuthUser;
import soongsil.kidbean.server.global.dto.ResponseTemplate;
import soongsil.kidbean.server.member.application.MypageService;

@RestController
@RequiredArgsConstructor
@RequestMapping("mypage/solved")
public class MypageController {

    private final MypageService mypageService;

    @GetMapping("/image/list")
    public ResponseEntity<ResponseTemplate<Object>> findSolvedImageList(
            @AuthenticationPrincipal AuthUser user,
            @RequestParam boolean isCorrect
    ) {
        return ResponseEntity.ok(ResponseTemplate.from(mypageService.findSolvedImage(user.memberId(), isCorrect)));
    }

    @GetMapping("/image/{solvedId}")
    public ResponseEntity<ResponseTemplate<Object>> findSolvedImageDetail(
            @PathVariable(name = "solvedId") Long solvedId) {
        return ResponseEntity.ok(ResponseTemplate.from(mypageService.solvedImageDetail(solvedId)));
    }

    @GetMapping("/word/list")
    public ResponseEntity<ResponseTemplate<Object>> findSolvedWordList(
            @AuthenticationPrincipal AuthUser user) {
        return ResponseEntity.ok(ResponseTemplate.from(mypageService.findSolvedWord(user.memberId())));
    }

    @GetMapping("/word/{solvedId}")
    public ResponseEntity<ResponseTemplate<Object>> findSolvedWordDetail(
            @PathVariable(name = "solvedId") Long solvedId) {
        return ResponseEntity.ok(ResponseTemplate.from(mypageService.solvedWordDetail(solvedId)));
    }

    @GetMapping("/voice/list")
    public ResponseEntity<ResponseTemplate<Object>> findSolvedVoiceList(
            @AuthenticationPrincipal AuthUser user) {
        return ResponseEntity.ok(ResponseTemplate.from(mypageService.findSolvedAnswerQuiz(user.memberId())));
    }

    @GetMapping("/voice/{solvedId}")
    public ResponseEntity<ResponseTemplate<Object>> findSolvedVoiceDetail(
            @PathVariable(name = "solvedId") Long solvedId) {
        return ResponseEntity.ok(ResponseTemplate.from(mypageService.solvedAnswerQuizDetail(solvedId)));
    }

    @GetMapping("/image/result")
    public ResponseEntity<ResponseTemplate<Object>> findImageQuizScoreInfo(
            @AuthenticationPrincipal AuthUser user) {
        return ResponseEntity.ok(ResponseTemplate.from(mypageService.findImageQuizScore(user.memberId())));
    }
}
