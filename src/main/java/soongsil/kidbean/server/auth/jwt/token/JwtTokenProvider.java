package soongsil.kidbean.server.auth.jwt.token;

import static soongsil.kidbean.server.member.exception.errorcode.MemberErrorCode.MEMBER_NOT_FOUND;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.exception.MemberNotFoundException;
import soongsil.kidbean.server.member.repository.MemberRepository;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final MemberRepository memberRepository;

    private static final String AUTH_KEY = "auth";

    private Key key;

    @PostConstruct
    public void setKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretKey());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * AccessToken 생성 메소드
     */
    public String createAccessToken(Member member, Authentication authentication) {
        long now = (new Date()).getTime();

        Date accessValidity = new Date(now + jwtProperties.getAccessTokenExpiration());

        log.info("expire: {}", accessValidity);

        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Claims claims = createClaims(member, authorities);

        //나중에 email 필요하면 넣어주기
        //여기서 setClaim하면 덮어씌여짐. -> 해결하기
        return Jwts.builder()
                // 토큰의 발급 시간을 기록
                .setIssuedAt(new Date(now))
                .setExpiration(accessValidity)
                // 토큰을 발급한 주체를 설정
                .setIssuer(jwtProperties.getIssuer())
                .addClaims(claims)
                // 토큰이 JWT 타입 명시
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .signWith(key, SignatureAlgorithm.HS256)
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
                .setExpiration(refreshValidity)
                // 토큰을 발급한 주체를 설정
                .setIssuer(jwtProperties.getIssuer())
                // 토큰이 JWT 타입 명시
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(final String token) {
        try {
            log.info("now date: {}", new Date());
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            log.info("expire date: {}", claims.getBody().getExpiration());
            return claims.getBody().getExpiration().after(new Date());
        } catch (Exception e) {
            log.error("Token validation error: ", e);
            return false;
        }
    }

    public Member getMember(String token) {
        String socialId = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();

        log.info("in getMember() socialId: {}", socialId);

        return memberRepository.findBySocialId(socialId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
    }

    private Claims createClaims(Member member, String authorities) {

        Claims claims = Jwts.claims().setSubject(member.getSocialId()); // 사용자 정의 클레임 추가
        claims.put(AUTH_KEY, authorities); // 여기에 필요한 추가 클레임들을 넣습니다

        return claims;
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}