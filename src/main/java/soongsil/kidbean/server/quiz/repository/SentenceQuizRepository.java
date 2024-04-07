package soongsil.kidbean.server.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quiz.domain.SentenceQuiz;

import java.util.Optional;

@Repository
public interface SentenceQuizRepository extends JpaRepository<SentenceQuiz, Long> {
    Optional<SentenceQuiz> findByQuizIdAndMember(Long quizId, Member member);
}
