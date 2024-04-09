package soongsil.kidbean.server.member.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soongsil.kidbean.server.member.dto.response.SolvedImageListResponse;
import soongsil.kidbean.server.member.dto.response.SolvedRecordListResponse;
import soongsil.kidbean.server.member.dto.response.SolvedVoiceDetailResponse;
import soongsil.kidbean.server.member.dto.response.SolvedWordDetailResponse;
import soongsil.kidbean.server.member.application.QuizSolvedService;
import soongsil.kidbean.server.member.dto.response.SolvedImageDetailResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("mypage/solved")
public class QuizSolvedController {

    private final QuizSolvedService quizSolvedService;

    @GetMapping("/image/list/{memberId}")
    public ResponseEntity<SolvedImageListResponse> findSolvedImageList(
            @PathVariable(name = "memberId") Long memberId) {
        return ResponseEntity.ok(quizSolvedService.findSolvedImage(memberId));
    }

    @GetMapping("/image/{solvedId}")
    public ResponseEntity<SolvedImageDetailResponse> findSolvedImageDetail(
            @PathVariable(name = "solvedId") Long solvedId) {
        return ResponseEntity.ok(quizSolvedService.solvedImageDetail(solvedId));
    }

    @GetMapping("/word/list/{memberId}")
    public ResponseEntity<SolvedRecordListResponse> findSolvedWordList(
            @PathVariable(name = "memberId") Long memberId) {
        return ResponseEntity.ok(quizSolvedService.findSolvedWord(memberId));
    }

    @GetMapping("/word/{solvedId}")
    public ResponseEntity<SolvedWordDetailResponse> findSolvedWordDetail(
            @PathVariable(name = "solvedId") Long solvedId) {
        return ResponseEntity.ok(quizSolvedService.solvedWordDetail(solvedId));
    }

    @GetMapping("/voice/list/{memberId}")
    public ResponseEntity<SolvedRecordListResponse> findSolvedVoiceList(
            @PathVariable(name = "memberId") Long memberId
    ) {
        return ResponseEntity.ok(quizSolvedService.findSolvedVoice(memberId));
    }

    @GetMapping("/voice/{solvedId}")
    public ResponseEntity<SolvedVoiceDetailResponse> findSolvedVoiceDetail(
            @PathVariable(name = "solvedId") Long solvedId) {
        return ResponseEntity.ok(quizSolvedService.solvedVoiceDetail(solvedId));
    }
}
