package soongsil.kidbean.server.auth.jwt.kakao;

import jakarta.transaction.Transactional;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class KakaoMemberDetailsService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public OAuth2User loadUser(final OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        KakaoUserInfo kakaoUserInfo = new KakaoUserInfo(oAuth2User.getAttributes());

        Member member = memberRepository.findByEmail(kakaoUserInfo.getEmail())
                .orElseGet(() ->
                        memberRepository.save(
                                Member.createFirstLoginMember(kakaoUserInfo.getEmail())
                        )
                );

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(member.getRole().name());

        return new KakaoMemberDetails(member.getMemberId(),
                String.valueOf(member.getEmail()),
                Collections.singletonList(authority),
                oAuth2User.getAttributes());
    }
}