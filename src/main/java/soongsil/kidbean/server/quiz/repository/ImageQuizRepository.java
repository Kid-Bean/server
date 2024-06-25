package soongsil.kidbean.server.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quiz.domain.ImageQuiz;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageQuizRepository extends JpaRepository<ImageQuiz, Long> {

    //해당 카테고리 row 개수
    @Query("SELECT count(*) FROM ImageQuiz iq WHERE iq.member = :member OR iq.member.role = 'ADMIN'")
    Integer countByMemberOrAdmin(Member member);

    List<ImageQuiz> findByMemberAndQuizIdIn(Member member, List<Long> quizIdList);

    Optional<ImageQuiz> findByQuizIdAndMember_MemberId(Long quizId, Long memberId);

    List<ImageQuiz> findAllByMember_MemberId(Long memberId);
}
