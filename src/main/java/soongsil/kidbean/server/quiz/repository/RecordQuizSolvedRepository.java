package soongsil.kidbean.server.quiz.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quiz.domain.RecordQuizSolved;


public interface RecordQuizSolvedRepository extends JpaRepository<RecordQuizSolved, Long> {

    List<RecordQuizSolved> findAllByMemberAndSentenceQuizIsNotNull(Member member);

    List<RecordQuizSolved> findAllByMemberAndAnswerQuizIsNotNull(Member member);
}
