package soongsil.kidbean.server.quiz.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quiz.domain.QuizSolved;

@Repository
public interface QuizSolvedRepository extends JpaRepository<QuizSolved, Long> {
    List<QuizSolved> findAllByMemberAndImageQuizIsNotNull(Member member);

    List<QuizSolved> findAllByMemberAndWordQuizNotNull(Member member);
}
