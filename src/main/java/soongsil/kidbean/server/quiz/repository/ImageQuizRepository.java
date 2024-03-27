package soongsil.kidbean.server.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quiz.domain.ImageQuiz;

import java.util.Optional;

@Repository
public interface ImageQuizRepository extends JpaRepository<ImageQuiz, Long> {
    Optional<ImageQuiz> findByMemberAndQuizId(Member member, Long quizId);
}
