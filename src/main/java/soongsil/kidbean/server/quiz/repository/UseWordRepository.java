package soongsil.kidbean.server.quiz.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quiz.domain.AnswerQuizSolved;
import soongsil.kidbean.server.quiz.domain.UseWord;

@Repository
public interface UseWordRepository extends JpaRepository<UseWord, Long> {
    List<UseWord> findAllByAnswerQuizSolved(AnswerQuizSolved answerQuizSolved);

    @Query("SELECT uw FROM UseWord uw JOIN FETCH uw.answerQuizSolved aqs WHERE aqs.member = :member")
    List<UseWord> findAllByMemberFetchJoinUseWord(@Param("member") Member member);
}
