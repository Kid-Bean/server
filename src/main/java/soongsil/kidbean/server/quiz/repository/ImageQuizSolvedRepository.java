package soongsil.kidbean.server.quiz.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quiz.domain.ImageQuiz;
import soongsil.kidbean.server.quiz.domain.ImageQuizSolved;


@Repository
public interface ImageQuizSolvedRepository extends JpaRepository<ImageQuizSolved, Long> {

    boolean existsImageQuizSolvedByImageQuizAndMember(ImageQuiz imageQuiz, Member member);

    Optional<ImageQuizSolved> findByImageQuizAndMember(ImageQuiz imageQuiz, Member member);

    List<ImageQuizSolved> findAllByMember(Member member);
}
