package soongsil.kidbean.server.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import soongsil.kidbean.server.member.domain.type.OAuthType;

/**
 * 각 소셜에서 받아오는 데이터가 다르므로 소셜별로 데이터를 받는 데이터를 분기 처리하는 DTO 클래스
 */
@Builder
@AllArgsConstructor
@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;

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

    public String getSocialId() {
        return this.attributes.get("id").toString();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name(attributes.get("id").toString())
                .email(getEmailKakao(attributes))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static String getEmailKakao(Map<String, Object> attributes) {
        return ((Map<String, Object>) attributes.get("kakao_account")).get("email").toString();
    }
}