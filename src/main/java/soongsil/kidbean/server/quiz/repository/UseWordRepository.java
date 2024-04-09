package soongsil.kidbean.server.quiz.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.quiz.domain.AnswerQuizSolved;
import soongsil.kidbean.server.quiz.domain.UseWord;

@Repository
public interface UseWordRepository extends JpaRepository<UseWord, Long> {
    List<UseWord> findAllByAnswerQuizSolved(AnswerQuizSolved answerQuizSolved);
}
