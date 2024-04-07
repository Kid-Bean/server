package soongsil.kidbean.server.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.quiz.domain.WordQuizWord;

@Repository
public interface WordQuizWordRepository extends JpaRepository<WordQuizWord, Long> {
}
