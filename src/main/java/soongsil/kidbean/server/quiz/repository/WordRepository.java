package soongsil.kidbean.server.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.quiz.domain.Word;
import soongsil.kidbean.server.quiz.domain.WordQuiz;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {

    List<Word> findAllByWordQuiz(WordQuiz wordQuiz);
}
