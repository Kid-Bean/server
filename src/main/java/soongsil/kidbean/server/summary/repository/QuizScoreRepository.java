package soongsil.kidbean.server.summary.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quizsolve.domain.type.QuizCategory;
import soongsil.kidbean.server.summary.domain.QuizScore;

@Repository
public interface QuizScoreRepository extends JpaRepository<QuizScore, Long> {

    Optional<QuizScore> findByMemberAndQuizCategory(Member member, QuizCategory quizCategory);

    List<QuizScore> findAllByMember(Member member);

    @Query(value = "SELECT GET_LOCK(:key, 3000)", nativeQuery = true)
    void getLock(String key);

    @Query(value = "SELECT RELEASE_LOCK(:key)", nativeQuery = true)
    void releaseLock(String key);
}
