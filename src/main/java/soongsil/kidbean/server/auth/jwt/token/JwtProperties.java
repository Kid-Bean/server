package soongsil.kidbean.server.auth.jwt.token;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.access.expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.access.header}")
    private String accessTokenHeader;

    @Value("${jwt.refresh.expiration}")
    private long refreshTokenExpiration;

    @Value("${jwt.refresh.header}")
    private String refreshTokenHeader;
}