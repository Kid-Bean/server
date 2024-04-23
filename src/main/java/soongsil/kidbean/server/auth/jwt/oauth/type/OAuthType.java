package soongsil.kidbean.server.auth.jwt.oauth.type;

import lombok.Getter;

@Getter
public enum OAuthType {
    KAKAO("kakao"),
    NAVER("naver"),
    GOOGLE("google"),
    ;

    private final String key;

    OAuthType(String key) {
        this.key = key;
    }
}