package soongsil.kidbean.server.quiz.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.domain.type.Role;
import soongsil.kidbean.server.quiz.domain.AnswerQuiz;

@Repository
public interface AnswerQuizRepository extends JpaRepository<AnswerQuiz, Long> {

    Integer countByMemberOrMember_Role(Member member, Role role);

    Page<AnswerQuiz> findByMemberOrMember_Role(Member member, Role role, Pageable pageable);
}