package soongsil.kidbean.server.auth.jwt.kakao;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.repository.MemberRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoMemberDetailsService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @SneakyThrows
    @Transactional
    @Override
    public OAuth2User loadUser(final OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        KakaoUserInfo kakaoUserInfo = new KakaoUserInfo(oAuth2User.getAttributes());

        ObjectMapper objectMapper = new ObjectMapper();
//        log.info("userRequest: {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(userRequest));
        log.info("oAuth2User: {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(oAuth2User));

        //kakao, naver 등
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Member member = memberRepository.findByEmail(kakaoUserInfo.getEmail())
                .orElseGet(() ->
                        memberRepository.save(
                                Member.createFirstLoginMember(kakaoUserInfo.getEmail(), registrationId)
                        )
                );

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(member.getRole().getKey());

        return new KakaoMemberDetails(member.getMemberId(),
                String.valueOf(member.getEmail()),
                Collections.singletonList(authority),
                oAuth2User.getAttributes());
    }
}