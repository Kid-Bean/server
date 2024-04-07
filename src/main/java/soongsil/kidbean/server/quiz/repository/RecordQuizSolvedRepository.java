package soongsil.kidbean.server.quiz.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quiz.domain.AnswerQuizSolved;


public interface RecordQuizSolvedRepository extends JpaRepository<AnswerQuizSolved, Long> {

    List<AnswerQuizSolved> findAllByMemberAndWordQuizIsNotNull(Member member);

    List<AnswerQuizSolved> findAllByMemberAndAnswerQuizIsNotNull(Member member);
}
