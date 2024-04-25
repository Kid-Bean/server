package soongsil.kidbean.server.auth.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record AuthUser(
        Long memberId,
        String socialId,
        String name,
        String email,
        List<String> roles
) {

}