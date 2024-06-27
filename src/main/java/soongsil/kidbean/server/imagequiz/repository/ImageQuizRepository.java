package soongsil.kidbean.server.imagequiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.imagequiz.domain.ImageQuiz;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageQuizRepository extends JpaRepository<ImageQuiz, Long> {

    @Query(value = "SELECT iq.* FROM image_quiz iq " +
            "JOIN member m ON iq.member_id = m.member_id " +
            "WHERE iq.member_id = :memberId OR m.role = 'admin' " +
            "ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<ImageQuiz> findRandomQuizzesByMemberOrAdmin(@Param("memberId") Long memberId, @Param("limit") int limit);

    Optional<ImageQuiz> findByQuizIdAndMember_MemberId(Long quizId, Long memberId);

    List<ImageQuiz> findAllByMember_MemberId(Long memberId);
}
