package soongsil.kidbean.server.member.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soongsil.kidbean.server.global.dto.ResponseTemplate;
import soongsil.kidbean.server.member.application.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/info/{memberId}")
    public ResponseEntity<ResponseTemplate<Object>> findMemberInfo(
            @PathVariable(name = "memberId") Long memberId) {
        return ResponseEntity.ok(ResponseTemplate.from(memberService.findMemberInfo(memberId)));
    }
}
