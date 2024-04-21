package soongsil.kidbean.server.member.domain.type;

import lombok.Getter;

@Getter
public enum Role {
    //스프링 시큐리티의 권한 코드는 항상 "ROLE_" 로 시작해야 함 -> hasRole로 뒤의 ADMIN, MEMBER로 접근 가능
    ADMIN("ROLE_ADMIN"),
    MEMBER("ROLE_MEMBER"),
    GUEST("ROLE_GUEST"),
    ;

    private final String key;

    Role(String key) {
        this.key = key;
    }
}
