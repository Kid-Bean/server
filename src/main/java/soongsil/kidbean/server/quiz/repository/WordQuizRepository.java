package soongsil.kidbean.server.quiz.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.domain.type.Role;
import soongsil.kidbean.server.quiz.domain.WordQuiz;

import java.util.List;
import java.util.Optional;

@Repository
public interface WordQuizRepository extends JpaRepository<WordQuiz, Long> {

    Integer countByMemberOrMember_Role(Member member, Role role);

    Page<WordQuiz> findByMemberOrMember_Role(Member member, Role role, Pageable pageable);

    Optional<WordQuiz> findByQuizIdAndMember_MemberId(Long quizId, Long memberId);

    List<WordQuiz> findAllByMember_MemberId(Long memberId);
}
