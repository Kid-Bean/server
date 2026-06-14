package soongsil.kidbean.server.auth.application.jwt;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.repository.MemberRepository;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final MemberRepository memberRepository;

    private Key key;
    private JwtParser jwtParser;
    private static final String MEMBER_ROLE = "role";

    @PostConstruct
    public void setKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretKey());
        this.key = Keys.hmacShaKeyFor(keyBytes);
        // parser는 thread-safe하므로 한 번만 생성해 재사용한다 (요청마다 재생성 비용 제거).
        this.jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
    }

    /**
     * AccessToken 생성 메소드
     */
    public String createAccessToken(Member member) {
        long now = (new Date()).getTime();

        Date accessValidity = new Date(now + jwtProperties.getAccessTokenExpiration());

        return Jwts.builder()
                // 토큰의 발급 시간을 기록
                .setIssuedAt(new Date(now))
                .setExpiration(accessValidity)
                // 토큰을 발급한 주체를 설정
                .setIssuer(jwtProperties.getIssuer())
                .setSubject(member.getMemberId().toString())
                .addClaims(Map.of(MEMBER_ROLE, member.getRole().name()))
                // 토큰이 JWT 타입 명시
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * RefreshToken 생성
     */
    public String createRefreshToken(Member member) {
        long now = (new Date()).getTime();

        Date refreshValidity = new Date(now + jwtProperties.getRefreshTokenExpiration());

        return Jwts.builder()
                // 토큰의 발급 시간을 기록
                .setIssuedAt(new Date(now))
                .setExpiration(refreshValidity)
                // 토큰을 발급한 주체를 설정
                .setIssuer(jwtProperties.getIssuer())
                .setSubject(member.getMemberId().toString())
                .addClaims(Map.of(MEMBER_ROLE, member.getRole().name()))
                // 토큰이 JWT 타입 명시
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean validateToken(final String token) {
        try {
            Date expiration = jwtParser.parseClaimsJws(token).getBody().getExpiration();
            return expiration.after(new Date());
        } catch (Exception e) {
            log.warn("Token validation failed: {}", e.getMessage());
            return false;
        }
    }

    public Member getMember(String token) {
        Long id = Long.parseLong(jwtParser.parseClaimsJws(token).getBody().getSubject());

        return memberRepository.getByIdOrThrow(id);
    }
}