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
import soongsil.kidbean.server.member.exception.MemberNotFoundException;

import static soongsil.kidbean.server.member.exception.errorcode.MemberErrorCode.MEMBER_NOT_FOUND;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    /**
     * memberId로 회원을 조회하고 없으면 MemberNotFoundException을 던진다.
     * 여러 서비스에 중복되던 findById(...).orElseThrow(...) 패턴을 통합한다.
     */
    default Member getByIdOrThrow(Long memberId) {
        return findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
    }

    Optional<Member> findBySocialId(String socialId);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select m from Member m where m.memberId = :memberId")
    Optional<Member> findByIdOptimisticLock(Long memberId);

    List<Member> findAllByRole(Role role);

    @Query(value = "SELECT GET_LOCK(:key, 3000)", nativeQuery = true)
    void getLock(String key);

    @Query(value = "SELECT RELEASE_LOCK(:key)", nativeQuery = true)
    void releaseLock(String key);
}
