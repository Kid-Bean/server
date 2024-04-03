package soongsil.kidbean.server.mypage.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soongsil.kidbean.server.mypage.application.QuizSolvedService;
import soongsil.kidbean.server.mypage.dto.response.SolvedRecordListResponse;
import soongsil.kidbean.server.mypage.dto.response.SolvedImageDetailResponse;
import soongsil.kidbean.server.mypage.dto.response.SolvedImageListResponse;
import soongsil.kidbean.server.mypage.dto.response.SolvedSentenceDetailResponse;
import soongsil.kidbean.server.mypage.dto.response.SolvedVoiceDetailResponse;

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

    @GetMapping("/mypage/solved/sentence/list/{memberId}")
    public ResponseEntity<SolvedRecordListResponse> findSolvedSentenceList(
            @PathVariable(name = "memberId") Long memberId) {
        return ResponseEntity.ok(quizSolvedService.findSolvedSentence(memberId));
    }

    @GetMapping("/sentence/{solvedId}")
    public ResponseEntity<SolvedSentenceDetailResponse> findSolvedSentenceDetail(
            @PathVariable(name = "solvedId") Long solvedId) {
        return ResponseEntity.ok(quizSolvedService.solvedSentenceDetail(solvedId));
    }

    @GetMapping("/mypage/solved/voice/list/{memberId}")
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
