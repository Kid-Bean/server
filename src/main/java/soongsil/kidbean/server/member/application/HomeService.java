package soongsil.kidbean.server.member.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.member.application.type.HomeImageUrl;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.dto.response.HomeResponse;
import soongsil.kidbean.server.member.exception.MemberNotFoundException;
import soongsil.kidbean.server.member.repository.MemberRepository;

import static soongsil.kidbean.server.member.exception.errorcode.MemberErrorCode.MEMBER_NOT_FOUND;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HomeService {

    private final MemberRepository memberRepository;

    public HomeResponse getHomeInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));

        String s3Url = HomeImageUrl.getUrlByScore(member.getScore());

        return HomeResponse.from(member, s3Url);
    }
}
