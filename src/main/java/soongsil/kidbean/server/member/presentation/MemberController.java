package soongsil.kidbean.server.member.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import soongsil.kidbean.server.auth.dto.AuthUser;
import soongsil.kidbean.server.global.dto.ResponseTemplate;
import soongsil.kidbean.server.member.application.MemberService;
import soongsil.kidbean.server.member.dto.request.MemberInfoRequest;

import static soongsil.kidbean.server.global.dto.ResponseTemplate.EMPTY_RESPONSE;

@Tag(name = "Member", description = "Member 관련 API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/info")
    public ResponseEntity<ResponseTemplate<Object>> findMemberInfo(
            @AuthenticationPrincipal AuthUser user) {
        return ResponseEntity.ok(ResponseTemplate.from(memberService.findMemberInfo(user.memberId())));
    }

    @Operation(summary = "Member 입력하기", description = "아이 정보 입력하기")
    @PutMapping("/info")
    public ResponseEntity<ResponseTemplate<Object>> uploadMemberInfo(
            @AuthenticationPrincipal AuthUser user,
            @RequestBody MemberInfoRequest request) {
        memberService.uploadMemberInfo(request, user.memberId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }
}
