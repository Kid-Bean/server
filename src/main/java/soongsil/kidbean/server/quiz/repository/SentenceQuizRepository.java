package soongsil.kidbean.server.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.quiz.domain.SentenceQuiz;

@Repository
public interface SentenceQuizRepository extends JpaRepository<SentenceQuiz, Long> {
}
