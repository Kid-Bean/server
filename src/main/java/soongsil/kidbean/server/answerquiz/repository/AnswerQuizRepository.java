package soongsil.kidbean.server.answerquiz.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.answerquiz.domain.AnswerQuiz;
import soongsil.kidbean.server.answerquiz.dto.response.AnswerQuizResponse;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.domain.type.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnswerQuizRepository extends JpaRepository<AnswerQuiz, Long> {

    @Query("SELECT count(*) FROM AnswerQuiz aq WHERE aq.member.memberId = :memberId OR aq.member.role = 'ADMIN'")
    Long countByMemberId(Long memberId);

    @Query("SELECT aq FROM AnswerQuiz aq WHERE aq.member = :member OR aq.member.role = 'ADMIN'")
    List<AnswerQuiz> findSingleResultByMember(Member member, Pageable pageable);

    @Query("SELECT new soongsil.kidbean.server.answerquiz.dto.response.AnswerQuizResponse(aq.quizId, aq.question, aq.title) "
            + "FROM AnswerQuiz aq "
            + "WHERE aq.isDefault = TRUE OR aq.member.memberId = :memberId "
            + "ORDER BY RAND()")
    List<AnswerQuizResponse> findRandomQuizzesByMember(Long memberId, PageRequest request);

    Page<AnswerQuiz> findByMemberOrMember_Role(Member member, Role role, Pageable pageable);

    Optional<AnswerQuiz> findByQuizIdAndMember_MemberId(Long quizId, Long memberId);

    List<AnswerQuiz> findAllByMember_MemberId(Long memberId);
}
