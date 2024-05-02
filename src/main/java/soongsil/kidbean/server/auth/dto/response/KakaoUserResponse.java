package soongsil.kidbean.server.auth.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

@Builder
@JsonNaming(SnakeCaseStrategy.class)
public record KakaoUserResponse(
        Long id,
        Properties properties,
        KakaoAccount kakaoAccount
) {

    @JsonNaming(SnakeCaseStrategy.class)
    public record Properties(
            String nickname
    ) {

    }

    public record KakaoAccount(
            String email
    ) {
    }
}