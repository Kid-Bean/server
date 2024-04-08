package soongsil.kidbean.server.member.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soongsil.kidbean.server.member.dto.response.SolvedAnswerDetailResponse;
import soongsil.kidbean.server.member.dto.response.SolvedImageListResponse;
import soongsil.kidbean.server.member.dto.response.SolvedAnswerQuizListResponse;
import soongsil.kidbean.server.member.dto.response.SolvedWordDetailResponse;
import soongsil.kidbean.server.member.application.MypageService;
import soongsil.kidbean.server.member.dto.response.SolvedImageDetailResponse;
import soongsil.kidbean.server.member.dto.response.SolvedWordQuizListResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("mypage/solved")
public class MypageController {

    private final MypageService mypageService;

    @GetMapping("/image/list/{memberId}")
    public ResponseEntity<SolvedImageListResponse> findSolvedImageList(
            @PathVariable(name = "memberId") Long memberId) {
        return ResponseEntity.ok(mypageService.findSolvedImage(memberId));
    }

    @GetMapping("/image/{solvedId}")
    public ResponseEntity<SolvedImageDetailResponse> findSolvedImageDetail(
            @PathVariable(name = "solvedId") Long solvedId) {
        return ResponseEntity.ok(mypageService.solvedImageDetail(solvedId));
    }

    @GetMapping("/word/list/{memberId}")
    public ResponseEntity<SolvedWordQuizListResponse> findSolvedWordList(
            @PathVariable(name = "memberId") Long memberId) {
        return ResponseEntity.ok(mypageService.findSolvedWord(memberId));
    }

    @GetMapping("/word/{solvedId}")
    public ResponseEntity<SolvedWordDetailResponse> findSolvedWordDetail(
            @PathVariable(name = "solvedId") Long solvedId) {
        return ResponseEntity.ok(mypageService.solvedWordDetail(solvedId));
    }

    @GetMapping("/voice/list/{memberId}")
    public ResponseEntity<SolvedAnswerQuizListResponse> findSolvedVoiceList(
            @PathVariable(name = "memberId") Long memberId
    ) {
        return ResponseEntity.ok(mypageService.findSolvedAnswerQuiz(memberId));
    }

    @GetMapping("/voice/{solvedId}")
    public ResponseEntity<SolvedAnswerDetailResponse> findSolvedVoiceDetail(
            @PathVariable(name = "solvedId") Long solvedId) {
        return ResponseEntity.ok(mypageService.solvedAnswerQuizDetail(solvedId));
    }
}
