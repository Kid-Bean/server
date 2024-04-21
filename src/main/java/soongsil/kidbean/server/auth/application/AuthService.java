package soongsil.kidbean.server.auth.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import soongsil.kidbean.server.auth.jwt.kakao.KakaoLoginRequest;
import soongsil.kidbean.server.auth.jwt.token.LoginResponse;
import soongsil.kidbean.server.auth.jwt.token.JwtTokenProvider;
import soongsil.kidbean.server.auth.jwt.token.TokenResponse;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.domain.type.Gender;
import soongsil.kidbean.server.member.domain.type.Role;
import soongsil.kidbean.server.member.repository.MemberRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private static final String REFRESH_HEADER = "RefreshToken";

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String kakaoClientSecret;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirect_uri;

    @Transactional
    public LoginResponse kakaoLogin(final String code) throws JsonProcessingException {
        String token = getToken(code);
        KakaoLoginRequest request = getKakaoUserInfo(token);

        Member member = saveIfNonExist(request);

        TokenResponse tokenResponse = jwtTokenProvider.createToken(String.valueOf(member.getMemberId()),
                member.getEmail(),
                member.getRole().name());

        return LoginResponse.of(tokenResponse.getAccessToken(), tokenResponse.getRefreshToken(), false);
    }

    @Transactional
    public LoginResponse kakaoLoginForPostman(final String code) throws JsonProcessingException {
        KakaoLoginRequest request = getKakaoUserInfo(code);
        Member member = saveIfNonExist(request);

        TokenResponse tokenResponse = jwtTokenProvider.createToken(String.valueOf(member.getMemberId()),
                member.getEmail(),
                member.getRole().name());

        return LoginResponse.of(tokenResponse.getAccessToken(), tokenResponse.getRefreshToken(), false);
    }

    @Transactional
    public Member saveIfNonExist(final KakaoLoginRequest request) {
        return memberRepository.findByEmail(request.getEmail())
                .orElseGet(() ->
                        memberRepository.save(
                                Member.builder()
                                        .role(Role.MEMBER)
                                        .gender(Gender.MAN)
                                        .score(0L)
                                        .build()
                        )
                );
    }

    private String getToken(final String code) throws JsonProcessingException {
        // HTTP 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 바디 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", kakaoClientId);
        body.add("client_secret", kakaoClientSecret);
        body.add("redirect_uri", redirect_uri);
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        String responseBody = response.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        log.info("token valie: {}", jsonNode.get("access_token").asText());

        return jsonNode.get("access_token").asText();
    }

    private KakaoLoginRequest getKakaoUserInfo(final String token) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();

        headers.add("Authorization", "Bearer " + token);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(headers);

        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(
                    "https://kapi.kakao.com/v2/user/me",
                    HttpMethod.POST,
                    kakaoTokenRequest,
                    String.class
            );
            String responseBody = response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            String email = jsonNode.get("kakao_account").get("email").asText();
            return new KakaoLoginRequest(email);
        } catch (HttpClientErrorException e) {
            throw new RuntimeException();
        }
    }

    private String getTokenFromHeader(final HttpServletRequest request, final String headerName) {
        String token = request.getHeader(headerName);
        if (StringUtils.hasText(token)) {
            return token;
        }
        return null;
    }
}