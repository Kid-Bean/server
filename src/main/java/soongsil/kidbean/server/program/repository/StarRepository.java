package soongsil.kidbean.server.program.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.program.domain.Program;
import soongsil.kidbean.server.program.domain.Star;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface StarRepository extends JpaRepository<Star, Long> {

    /**
     * 주어진 프로그램들 중 회원이 즐겨찾기한 프로그램 ID 집합을 한 번에 조회한다 (프로그램별 exists 조회 N+1 제거).
     */
    @Query("SELECT s.program.programId FROM Star s "
            + "WHERE s.member.memberId = :memberId AND s.program.programId IN :programIds")
    Set<Long> findStarredProgramIds(Long memberId, List<Long> programIds);

    Optional<Star> findByMemberAndProgram(Member member, Program program);
}
