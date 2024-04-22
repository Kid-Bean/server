package soongsil.kidbean.server.auth.jwt.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import soongsil.kidbean.server.auth.jwt.common.type.OAuthType;
import soongsil.kidbean.server.auth.jwt.kakao.KakaoOAuth2UserInfo;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.domain.type.Gender;
import soongsil.kidbean.server.member.domain.type.Role;

/**
 * 각 소셜에서 받아오는 데이터가 다르므로 소셜별로 데이터를 받는 데이터를 분기 처리하는 DTO 클래스
 */
@Builder
@AllArgsConstructor
@Getter
public class OAuthAttributes {

    private String nameAttributeKey; // OAuth2 로그인 진행 시 키가 되는 필드 값, PK와 같은 의미
    private OAuth2UserInfo oAuth2UserInfo; // 소셜 타입별 로그인 유저 정보(닉네임, 이메일, 프로필 사진 등등)

    /**
     * SocialType에 맞는 메소드 호출하여 OAuthAttributes 객체 반환 파라미터
     */
    public static OAuthAttributes of(OAuthType oAuthType,
                                     String userNameAttributeName,
                                     Map<String, Object> attributes) {
        if (oAuthType == OAuthType.KAKAO) {
            return ofKakao(userNameAttributeName, attributes);
        }

        return null;
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oAuth2UserInfo(new KakaoOAuth2UserInfo(attributes))
                .build();
    }

    /**
     * of메소드로 OAuthAttributes 객체가 생성되어, 유저 정보들이 담긴 OAuth2UserInfo가 소셜 타입별로 주입된 상태 OAuth2UserInfo에서 socialId(식별값),
     * nickname, imageUrl을 가져와서 build email에는 UUID로 중복 없는 랜덤 값 생성 role은 GUEST로 설정
     */
    public Member toMember(OAuthType oAuthType, OAuth2UserInfo oAuth2UserInfo) {
        return Member.builder()
                .email(oAuth2UserInfo.getEmail())
                .role(Role.GUEST)
                .gender(Gender.NONE)
                .oAuthType(oAuthType)
                .score(0L)
                .build();
    }
}