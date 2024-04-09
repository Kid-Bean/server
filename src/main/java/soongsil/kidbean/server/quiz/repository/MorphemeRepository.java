package soongsil.kidbean.server.quiz.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.quiz.domain.AnswerQuizSolved;
import soongsil.kidbean.server.quiz.domain.Morpheme;

@Repository
public interface MorphemeRepository extends JpaRepository<Morpheme, Long> {
    List<Morpheme> findAllByAnswerQuizSolved(AnswerQuizSolved answerQuizSolved);
}
