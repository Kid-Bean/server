package soongsil.kidbean.server.program.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.program.domain.Program;
import soongsil.kidbean.server.program.domain.Star;

import java.util.Optional;

@Repository
public interface StarRepository extends JpaRepository<Star, Long> {

    boolean existsByMemberAndProgram(Member member, Program program);

    Optional<Star> findByMemberAndProgram(Member member, Program program);
}
