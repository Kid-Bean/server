package soongsil.kidbean.server.wordquiz.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.wordquiz.domain.WordQuiz;

import java.util.List;
import java.util.Optional;

@Repository
public interface WordQuizRepository extends JpaRepository<WordQuiz, Long> {

    @Query("SELECT count(*) FROM WordQuiz wq WHERE wq.member = :member OR wq.member.role = 'ADMIN'")
    Integer countByMemberOrAdmin(Member member);

    @Query("SELECT wq FROM WordQuiz wq JOIN FETCH wq.words WHERE wq.member = :member OR wq.member.role = 'ADMIN'")
    List<WordQuiz> findSingleResultByMember(Member member, Pageable pageable);

    @Query("SELECT wq.quizId FROM WordQuiz wq WHERE wq.member = :member OR wq.member.role = 'ADMIN' ORDER BY RAND() LIMIT :quizNum")
    List<Long> findRandomWordQuizIds(Member member, int quizNum);

    @Query("SELECT wq FROM WordQuiz wq JOIN FETCH wq.words w WHERE wq.quizId IN :quizIds")
    List<WordQuiz> findByIdsWithWords(List<Long> quizIds);


    Optional<WordQuiz> findByQuizIdAndMember_MemberId(Long quizId, Long memberId);

    List<WordQuiz> findAllByMember_MemberId(Long memberId);
}
