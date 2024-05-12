package soongsil.kidbean.server.quiz.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quiz.domain.AnswerQuizSolved;
import soongsil.kidbean.server.quiz.domain.UseWord;

@Repository
public interface UseWordRepository extends JpaRepository<UseWord, Long> {

    List<UseWord> findTop5ByMemberOrderByCountDesc(Member member);

    @Query("SELECT uw.wordName, uw.count FROM UseWord uw WHERE uw.member = :member")
    Map<String,Long> findWordCountsForMember(@Param("member") Member member);

    Optional<UseWord> findByWordNameAndMember(String wordName, Member member);

    List<UseWord> findAllByMember(Member member);
}
