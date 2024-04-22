package soongsil.kidbean.server.auth.jwt.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import soongsil.kidbean.server.auth.jwt.kakao.KakaoMemberDetails;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    public final String BEARER_PREFIX = "Bearer ";
    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String AUTH_ID = "ID";
    private static final String AUTH_KEY = "AUTHORITY";
    private static final String AUTH_EMAIL = "EMAIL";

    private String secretKey;
    private Long accessTokenValidityMilliSeconds;
    private Long refreshTokenValidityMilliSeconds;

    private Key key;

    @Autowired
    public JwtTokenProvider(@Value("${jwt.secret-key}") String secret,
                            @Value("${jwt.access.expiration}") long accessTokenValidityInSeconds,
                            @Value("${jwt.refresh.expiration}") long refreshTokenValidityInMilliseconds,
                            JwtProperties jwtProperties
                            //RedisTemplate redisTemplate*
    ) {
        this.secretKey = secret;
        this.accessTokenValidityMilliSeconds = accessTokenValidityInSeconds * 1000;
        this.refreshTokenValidityMilliSeconds = refreshTokenValidityInMilliseconds * 1000;
        this.jwtProperties = jwtProperties;
    }

    @PostConstruct
    public void setKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String resolveRefreshToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(jwtProperties.getRefreshTokenHeader());
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public TokenResponse createToken(String memberId, String email, String role) {
        long now = (new Date()).getTime();

        Date accessValidity = new Date(now + this.accessTokenValidityMilliSeconds);
        Date refreshValidity = new Date(now + this.refreshTokenValidityMilliSeconds);

        String accessToken = Jwts.builder()
                // 토큰의 발급 시간을 기록
                .setIssuedAt(new Date(now))
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(accessValidity)
                .setSubject(ACCESS_TOKEN_SUBJECT)
                // 토큰을 발급한 주체를 설정
                .setIssuer(jwtProperties.getIssuer())
                // 토큰이 JWT 타입 명시
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .compact();

        String refreshToken = Jwts.builder()
                // 토큰의 발급 시간을 기록
                .setIssuedAt(new Date(now))
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(refreshValidity)
                .setSubject(REFRESH_TOKEN_SUBJECT)
                // 토큰을 발급한 주체를 설정
                .setIssuer(jwtProperties.getIssuer())
                // 토큰이 JWT 타입 명시
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .compact();

        return TokenResponse.of(accessToken, refreshToken);
    }

    public boolean validateToken(final String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(jwtProperties.getSecretKey())
                    .build()
                    .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);

        List<String> authorities = Arrays.asList(claims.get(AUTH_KEY)
                .toString()
                .split(","));

        List<? extends GrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        // TODO 나중에 KakaoMemberDetails가 아니라 KakaoOAuth2UserInfo로 만들기
        KakaoMemberDetails principal = new KakaoMemberDetails(Long.parseLong((String) claims.get(AUTH_ID)),
                (String) claims.get(AUTH_EMAIL),
                simpleGrantedAuthorities, Map.of());

        return new UsernamePasswordAuthenticationToken(principal, token, simpleGrantedAuthorities);
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}