package soongsil.kidbean.server.member.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.auth.jwt.oauth.type.OAuthType;
import soongsil.kidbean.server.member.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    Optional<Member> findByoAuthTypeAndSocialId(OAuthType oAuthType, String socialId);

    Optional<Member> findBySocialId(String socialId);
}
