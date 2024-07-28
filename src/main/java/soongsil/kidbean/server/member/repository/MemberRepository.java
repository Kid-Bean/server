package soongsil.kidbean.server.member.repository;

import jakarta.persistence.LockModeType;
import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.domain.type.Role;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findBySocialId(String socialId);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select m from Member m where m.memberId = :memberId")
    Optional<Member> findByIdOptimisticLock(Long memberId);

    List<Member> findAllByRole(Role role);
}
