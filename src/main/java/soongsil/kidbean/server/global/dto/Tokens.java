package soongsil.kidbean.server.global.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class Tokens {

    private String accessToken;

    private String refreshToken;
}