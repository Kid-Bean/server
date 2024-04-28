package soongsil.kidbean.server.auth.util.kakao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import soongsil.kidbean.server.auth.dto.response.KakaoUserResponse;
import soongsil.kidbean.server.auth.util.SocialLoginProvider;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.domain.type.Gender;
import soongsil.kidbean.server.member.domain.type.OAuthType;
import soongsil.kidbean.server.member.domain.type.Role;

@Service
@RequiredArgsConstructor
public class KakaoLoginProvider implements SocialLoginProvider {

    private final WebClient webClient;

    @Override
    public Member getUserData(String accessToken) {
        KakaoUserResponse kakaoUserResponse = webClient.get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(KakaoUserResponse.class)
                .block();

        return Member.builder()
                .email(kakaoUserResponse.getKakaoAccount().getEmail())
                .role(Role.GUEST)
                .gender(Gender.NONE)
                .socialId(kakaoUserResponse.getId().toString())    //social id
                .oAuthType(OAuthType.KAKAO)
                .score(0L)
                .build();
    }
}
