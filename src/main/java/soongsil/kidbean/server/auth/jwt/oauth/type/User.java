package soongsil.kidbean.server.auth.jwt.oauth.type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private String socialId;
    private String email;
    private List<String> roles;
}