package soongsil.kidbean.server.auth.jwt.common.type;

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