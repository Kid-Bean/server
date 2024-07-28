package soongsil.kidbean.server.quizsolve.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.imagequiz.domain.ImageQuiz;
import soongsil.kidbean.server.quizsolve.domain.QuizSolved;

@Repository
public interface QuizSolvedRepository extends JpaRepository<QuizSolved, Long> {

    List<QuizSolved> findAllByMemberAndIsCorrectAndImageQuizIsNotNull(Member member, boolean isCorrect);

    List<QuizSolved> findAllByMemberAndIsCorrectTrue(Member member);

    List<QuizSolved> findAllByMemberAndWordQuizNotNull(Member member);

    List<QuizSolved> findAllByImageQuizAndIsCorrectTrue(ImageQuiz imageQuiz);
}
