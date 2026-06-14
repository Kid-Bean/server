package soongsil.kidbean.server.quizsolve.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quizsolve.domain.UseWord;

@Repository
public interface UseWordRepository extends JpaRepository<UseWord, Long> {

    List<UseWord> findTop5ByMemberOrderByCountDesc(Member member);

    List<UseWord> findAllByMember(Member member);
}
