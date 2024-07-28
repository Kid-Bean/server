package soongsil.kidbean.server.summary.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.quizsolve.domain.type.QuizCategory;
import soongsil.kidbean.server.summary.domain.AverageScore;
import soongsil.kidbean.server.summary.domain.type.AgeGroup;

@Repository
public interface AverageScoreRepository extends JpaRepository<AverageScore, Long> {

    Optional<AverageScore> findByAgeGroupAndQuizCategory(AgeGroup ageGroup, QuizCategory quizCategory);

    List<AverageScore> findAllByAgeGroup(AgeGroup ageGroup);
}
