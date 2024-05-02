package soongsil.kidbean.server.quiz.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quiz.domain.WordQuiz;

import java.util.List;
import java.util.Optional;

@Repository
public interface WordQuizRepository extends JpaRepository<WordQuiz, Long> {

    @Query("SELECT count(*) FROM WordQuiz wq WHERE wq.member = :member OR wq.member.role = 'ADMIN'")
    Integer countByMemberOrAdmin(Member member);

    @Query("SELECT wq FROM WordQuiz wq WHERE wq.member = :member OR wq.member.role = 'ADMIN'")
    Page<WordQuiz> findSinglePageByMember(Member member, Pageable pageable);

    Optional<WordQuiz> findByQuizIdAndMember_MemberId(Long quizId, Long memberId);

    List<WordQuiz> findAllByMember_MemberId(Long memberId);
}
