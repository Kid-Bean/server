package soongsil.kidbean.server.quizsolve.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quizsolve.domain.QuizSolved;

@Repository
public interface QuizSolvedRepository extends JpaRepository<QuizSolved, Long> {

    @Query("SELECT qs FROM QuizSolved qs JOIN FETCH qs.imageQuiz "
            + "WHERE qs.member = :member AND qs.isCorrect = :isCorrect AND qs.imageQuiz IS NOT NULL")
    List<QuizSolved> findAllByMemberAndIsCorrectAndImageQuizIsNotNull(Member member, boolean isCorrect);

    @Query("SELECT qs FROM QuizSolved qs JOIN FETCH qs.wordQuiz "
            + "WHERE qs.member = :member AND qs.wordQuiz IS NOT NULL")
    List<QuizSolved> findAllByMemberAndWordQuizNotNull(Member member);

    /**
     * 어드민이 등록한 ImageQuiz의 풀이 기록을 풀이자(member)/문제(imageQuiz)와 함께 한 번에 조회한다.
     * 스케줄러가 lazy 연관에 접근하며 발생하던 N+1 / LazyInitializationException 을 방지한다.
     */
    @Query("SELECT qs FROM QuizSolved qs "
            + "JOIN FETCH qs.imageQuiz i "
            + "JOIN FETCH qs.member "
            + "WHERE i.member.role = 'ADMIN'")
    List<QuizSolved> findAllAdminImageQuizSolved();

    /**
     * 여러 회원의 정답 풀이를 풀이자/문제와 함께 한 번에 조회한다 (회원별 개별 조회 N+1 제거).
     */
    @Query("SELECT qs FROM QuizSolved qs "
            + "JOIN FETCH qs.member m "
            + "LEFT JOIN FETCH qs.imageQuiz "
            + "LEFT JOIN FETCH qs.wordQuiz "
            + "WHERE m IN :members AND qs.isCorrect = true")
    List<QuizSolved> findAllCorrectByMembers(List<Member> members);
}
