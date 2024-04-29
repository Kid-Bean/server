package soongsil.kidbean.server.auth.util.kakao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soongsil.kidbean.server.auth.dto.response.KakaoUserResponse;
import soongsil.kidbean.server.auth.util.SocialLoginProvider;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.domain.type.Gender;
import soongsil.kidbean.server.member.domain.type.OAuthType;
import soongsil.kidbean.server.member.domain.type.Role;

@Service
@RequiredArgsConstructor
public class KakaoLoginProvider implements SocialLoginProvider {

    private final KakaoClient kakaoClient;

    @Override
    public Member getUserData(String accessToken) {
        KakaoUserResponse kakaoUserResponse = kakaoClient.getUserData("Bearer " + accessToken);

        return Member.builder()
                .email(kakaoUserResponse.kakaoAccount().email())
                .role(Role.GUEST)
                .gender(Gender.NONE)
                .socialId(kakaoUserResponse.id().toString())    //social id
                .oAuthType(OAuthType.KAKAO)
                .score(0L)
                .build();
    }
}
