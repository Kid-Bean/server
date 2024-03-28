package soongsil.kidbean.server.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soongsil.kidbean.server.quiz.domain.RecordQuizSolved;


public interface RecordQuizSolvedRepository extends JpaRepository<RecordQuizSolved, Long> {

}
