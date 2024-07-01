package soongsil.kidbean.server.imagequiz.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.imagequiz.domain.ImageQuiz;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageQuizRepository extends JpaRepository<ImageQuiz, Long> {

    @Query("SELECT count(*) FROM ImageQuiz iq "
            + "WHERE iq.isDefault = TRUE OR iq.member.memberId = :memberId")
    Long countByMemberId(Long memberId);

    @Query("SELECT iq FROM ImageQuiz iq "
            + "WHERE iq.isDefault = TRUE OR iq.member.memberId = :memberId "
            + "ORDER BY iq.randVal")
    List<ImageQuiz> findRandomQuizzesByMember(Long memberId, PageRequest request);

    Optional<ImageQuiz> findByQuizIdAndMember_MemberId(Long quizId, Long memberId);

    List<ImageQuiz> findAllByMember_MemberId(Long memberId);
}
