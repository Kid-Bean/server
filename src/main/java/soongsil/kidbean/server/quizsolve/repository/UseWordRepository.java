package soongsil.kidbean.server.quizsolve.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quizsolve.domain.UseWord;

@Repository
public interface UseWordRepository extends JpaRepository<UseWord, Long> {

    List<UseWord> findTop5ByMemberOrderByCountDesc(Member member);

    Optional<UseWord> findByWordNameAndMember(String wordName, Member member);

    List<UseWord> findAllByMember(Member member);
}
