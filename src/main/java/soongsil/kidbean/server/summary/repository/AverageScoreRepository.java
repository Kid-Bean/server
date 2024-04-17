package soongsil.kidbean.server.summary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.summary.domain.AverageScore;

@Repository
public interface AverageScoreRepository extends JpaRepository<AverageScore, Long> {
}
