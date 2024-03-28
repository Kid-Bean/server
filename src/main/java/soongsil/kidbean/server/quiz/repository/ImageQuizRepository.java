package soongsil.kidbean.server.quiz.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quiz.domain.ImageQuiz;

import java.util.Optional;
import soongsil.kidbean.server.quiz.domain.type.Category;

@Repository
public interface ImageQuizRepository extends JpaRepository<ImageQuiz, Long> {
    Optional<ImageQuiz> findByMemberAndQuizId(Member member, Long quizId);

    //해당 카테고리의 row 개수
    Integer countByMemberAndCategory(Member member, Category category);

    Page<ImageQuiz> findAllByMemberAndCategory(Member member, Category category, Pageable pageable);
}
