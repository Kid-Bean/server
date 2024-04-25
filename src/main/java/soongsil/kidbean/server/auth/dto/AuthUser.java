package soongsil.kidbean.server.auth.dto;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
public class AuthUser implements Serializable {
    private Long memberId;
    private String socialId;
    private String name;
    private String email;
    private List<String> roles;
}