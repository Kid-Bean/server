package soongsil.kidbean.server.quiz.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quiz.domain.ImageQuiz;
import soongsil.kidbean.server.quiz.domain.QuizSolved;


@Repository
public interface QuizSolvedRepository extends JpaRepository<QuizSolved, Long> {

    boolean existsImageQuizSolvedByImageQuizAndMember(ImageQuiz imageQuiz, Member member);

    Optional<QuizSolved> findByImageQuizAndMember(ImageQuiz imageQuiz, Member member);

    List<QuizSolved> findAllByMember(Member member);
}
