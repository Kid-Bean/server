package soongsil.kidbean.server.auth.dto;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import soongsil.kidbean.server.member.domain.Member;

public record CustomUserDetails(
        Member member
) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(member.getRoleKey()));
    }

    @Override
    public String getPassword() {
        //비밀번호는 소셜 로그인에 사용 X
        return null;
    }

    @Override
    public String getUsername() {
        //username은 소셜 로그인에 사용 X
        return member.getMemberId().toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}