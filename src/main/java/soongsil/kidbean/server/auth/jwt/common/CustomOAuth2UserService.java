package soongsil.kidbean.server.auth.jwt.common;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import soongsil.kidbean.server.auth.jwt.common.type.OAuthType;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.repository.MemberRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        /**
         * userRequest에서 registrationId 추출 후 registrationId으로 SocialType 저장
         * http://localhost:8080/oauth2/authorization/kakao에서 kakao가 registrationId
         * userNameAttributeName은 이후에 nameAttributeKey로 설정된다.
         */
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuthType socialType = getSocialType(registrationId);

        String userNameAttributeName = userRequest
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName(); // OAuth2 로그인 시 키(PK)가 되는 값

        Map<String, Object> attributes = oAuth2User.getAttributes(); // 소셜 로그인에서 API가 제공하는 userInfo의 Json 값(유저 정보들)

        //socialType에 따라 유저 정보를 통해 OAuthAttributes 객체 생성
        OAuthAttributes extractAttributes = OAuthAttributes.of(socialType, userNameAttributeName, attributes);

        //Member 객체 생성 후 반환
        Member createdUser = getMember(Objects.requireNonNull(extractAttributes), socialType);

        //DefaultOAuth2User를 구현한 CustomOAuth2User 객체를 생성해서 반환
        return new CustomOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(createdUser.getRole().getKey())),
                attributes,
                extractAttributes.getNameAttributeKey(),
                createdUser.getEmail(),
                createdUser.getRole()
        );
    }

    private OAuthType getSocialType(String registrationId) {
        if (OAuthType.KAKAO.getKey().equals(registrationId)) {
            return OAuthType.KAKAO;
        }
        return null;
    }

    /**
     * SocialType과 attributes에 들어있는 소셜 로그인의 식별값 id를 통해 회원을 찾아 반환하는 메소드 만약 찾은 회원이 있다면, 그대로 반환하고 없다면 saveUser()를 호출하여 회원을
     * 저장한다.
     */
    private Member getMember(OAuthAttributes attributes, OAuthType oAuthType) {

        //socialId와 oAuthType으로 유저 존재 확인
        return memberRepository.findByoAuthTypeAndSocialId(oAuthType, attributes.getOAuth2UserInfo().getId())
                .orElseGet(() ->
                        memberRepository.save(
                                attributes.toMember(oAuthType, attributes.getOAuth2UserInfo())
                        )
                );
    }
}