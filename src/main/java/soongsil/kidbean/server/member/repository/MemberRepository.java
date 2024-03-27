package soongsil.kidbean.server.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.member.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
