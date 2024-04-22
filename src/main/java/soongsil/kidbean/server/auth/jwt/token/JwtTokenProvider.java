package soongsil.kidbean.server.auth.jwt.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import soongsil.kidbean.server.auth.jwt.common.CustomOAuth2User;
import soongsil.kidbean.server.auth.jwt.kakao.KakaoMemberDetails;
import soongsil.kidbean.server.member.domain.type.Role;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private static final String AUTH_ROLE = "role";
    private final JwtProperties jwtProperties;
    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String AUTH_ID = "ID";
    private static final String AUTH_KEY = "auth";
    private static final String AUTH_EMAIL = "EMAIL";

    private Key key;

    @PostConstruct
    public void setKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretKey());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * AccessToken 생성 메소드
     */
    public String createAccessToken(Authentication authentication) {
        long now = (new Date()).getTime();

        Date accessValidity = new Date(now + jwtProperties.getAccessTokenExpiration());

        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Claims claims = createClaims(authorities);

        //나중에 email 필요하면 넣어주기
        return Jwts.builder()
                // 토큰의 발급 시간을 기록
                .setIssuedAt(new Date(now))
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(accessValidity)
                .setSubject(ACCESS_TOKEN_SUBJECT)
                // 토큰을 발급한 주체를 설정
                .setIssuer(jwtProperties.getIssuer())
                // 토큰이 JWT 타입 명시
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .compact();
    }

    /**
     * RefreshToken 생성 RefreshToken은 Claim에 email도 넣지 않으므로 withClaim() X
     */
    public String createRefreshToken() {
        long now = (new Date()).getTime();

        Date refreshValidity = new Date(now + jwtProperties.getRefreshTokenExpiration());

        return Jwts.builder()
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
    }

    public boolean validateToken(final String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);

        List<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTH_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // claims에서 이메일과 역할 정보 추출
        String email = (String) claims.get(AUTH_EMAIL);
        // 역할 정보는 예시로 "ROLE_USER"와 같은 형태의 문자열로 저장되어 있다고 가정
        String roleStr = (String) claims.get(AUTH_ROLE);

        Role role = Role.valueOf(roleStr);

        log.info("role: {}", roleStr);

        // CustomOAuth2User 객체 생성
        CustomOAuth2User principal = new CustomOAuth2User(
                authorities, Map.of(), "name", email, role);

        log.info("authorities: {}", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }


    private Claims createClaims(String authorities) {
        return (Claims) Jwts.claims()
                .put(AUTH_KEY, authorities);
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}