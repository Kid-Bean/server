package soongsil.kidbean.server.wordquiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.wordquiz.domain.Word;
import soongsil.kidbean.server.wordquiz.domain.WordQuiz;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {

    List<Word> findAllByWordQuiz(WordQuiz wordQuiz);
}
