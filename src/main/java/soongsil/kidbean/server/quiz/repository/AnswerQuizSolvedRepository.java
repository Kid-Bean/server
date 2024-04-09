package soongsil.kidbean.server.quiz.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quiz.domain.AnswerQuizSolved;

@Repository
public interface AnswerQuizSolvedRepository extends JpaRepository<AnswerQuizSolved, Long> {

    List<AnswerQuizSolved> findAllByMember(Member member);

    List<AnswerQuizSolved> findAllByMemberAndAnswerQuizIsNotNull(Member member);
}
