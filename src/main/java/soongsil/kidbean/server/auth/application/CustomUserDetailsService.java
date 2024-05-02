package soongsil.kidbean.server.auth.application;

import static soongsil.kidbean.server.member.exception.errorcode.MemberErrorCode.*;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import soongsil.kidbean.server.auth.dto.CustomUserDetails;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.exception.MemberNotFoundException;
import soongsil.kidbean.server.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String socialId) throws UsernameNotFoundException {
        Member member = memberRepository.findBySocialId(socialId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));

        if (member == null) {
            throw new UsernameNotFoundException("User not found with socialId: " + socialId);
        }

        return new CustomUserDetails(member);
    }
}