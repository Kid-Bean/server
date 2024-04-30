package soongsil.kidbean.server.summary.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quiz.application.vo.QuizType;
import soongsil.kidbean.server.quiz.domain.type.QuizCategory;
import soongsil.kidbean.server.summary.domain.ImageQuizScore;

@Repository
public interface ImageQuizScoreRepository extends JpaRepository<ImageQuizScore, Long> {
    Optional<ImageQuizScore> findByMemberAndQuizCategoryAndQuizType(Member member, QuizCategory quizCategory, QuizType quizType);

    List<ImageQuizScore> findAllByMemberAndQuizType(Member member, QuizType quizType);
}
