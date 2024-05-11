package soongsil.kidbean.server.member.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.dto.request.MemberInfoRequest;
import soongsil.kidbean.server.member.dto.response.MemberInfoResponse;
import soongsil.kidbean.server.member.exception.MemberNotFoundException;
import soongsil.kidbean.server.member.repository.MemberRepository;

import static soongsil.kidbean.server.member.exception.errorcode.MemberErrorCode.MEMBER_NOT_FOUND;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberInfoResponse findMemberInfo(Long memberId) {
        Member member = findMemberById(memberId);

        return MemberInfoResponse.from(member);
    }

    @Transactional
    public void uploadMemberInfo(MemberInfoRequest request, Long memberId) {
        Member member = findMemberById(memberId);
        member.uploadMember(request.name(), request.gender(), request.birthDate());
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(()-> new MemberNotFoundException(MEMBER_NOT_FOUND));
    }
}
