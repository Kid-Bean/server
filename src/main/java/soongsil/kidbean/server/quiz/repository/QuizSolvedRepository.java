package soongsil.kidbean.server.quiz.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quiz.domain.ImageQuiz;
import soongsil.kidbean.server.quiz.domain.QuizSolved;
import soongsil.kidbean.server.quiz.domain.WordQuiz;

@Repository
public interface QuizSolvedRepository extends JpaRepository<QuizSolved, Long> {

    boolean existsByImageQuizAndMember(ImageQuiz imageQuiz, Member member);

    boolean existsByWordQuizAndMember(WordQuiz wordQuiz, Member member);

    List<QuizSolved> findAllByMemberAndImageQuizIsNotNull(Member member);

    List<QuizSolved> findAllByMemberAndIsCorrectTrueAndImageQuizIsNotNull(Member member);

    List<QuizSolved> findAllByMemberAndWordQuizNotNull(Member member);

    boolean existsByImageQuizAndMemberAndIsCorrect(ImageQuiz imageQuiz, Member member, boolean isCorrect);

    boolean existsByWordQuizAndMemberAndIsCorrect(WordQuiz wordQuiz, Member member, boolean isCorrect);

    List<QuizSolved> findAllByImageQuizIsNotNull();

    List<QuizSolved> findAllByImageQuizAndIsCorrectTrue(ImageQuiz imageQuiz);
}
