package soongsil.kidbean.server.auth.util;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import soongsil.kidbean.server.auth.dto.AuthUser;
import soongsil.kidbean.server.member.domain.Member;

@RequiredArgsConstructor
@Component
public class AuthenticationUtil {

    public static Authentication getAuthentication(AuthUser authUser) {

        List<GrantedAuthority> grantedAuthorities = authUser.roles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(authUser, "", grantedAuthorities);
    }

    public static void makeAuthentication(Member member) {
        // Authentication 정보 만들기
        AuthUser authUser = AuthUser.builder()
                .memberId(member.getMemberId())
                .socialId(member.getSocialId())
                .email(member.getEmail())
                .roles(Collections.singletonList(member.getRole().getKey()))
                .build();

        // ContextHolder 에 Authentication 정보 저장
        Authentication auth = AuthenticationUtil.getAuthentication(authUser);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}