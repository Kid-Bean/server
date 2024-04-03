package soongsil.kidbean.server.quiz.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import soongsil.kidbean.server.quiz.domain.Analysis;
import soongsil.kidbean.server.quiz.domain.RecordQuizSolved;

public interface AnalysisRepository extends JpaRepository<Analysis, Long> {
    Optional<Analysis> findByRecordQuizSolved(RecordQuizSolved recordQuizSolved);
}