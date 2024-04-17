package soongsil.kidbean.server.member.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.domain.type.Role;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findAllByRole(Role role);
}
