package soongsil.kidbean.server.quizsolve.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.imagequiz.domain.ImageQuiz;
import soongsil.kidbean.server.quizsolve.domain.QuizSolved;
import soongsil.kidbean.server.wordquiz.domain.WordQuiz;

@Repository
public interface QuizSolvedRepository extends JpaRepository<QuizSolved, Long> {

    boolean existsByImageQuizAndMember(ImageQuiz imageQuiz, Member member);

    boolean existsByWordQuizAndMember(WordQuiz wordQuiz, Member member);

    List<QuizSolved> findAllByMemberAndIsCorrectAndImageQuizIsNotNull(Member member, boolean isCorrect);

    List<QuizSolved> findAllByMemberAndIsCorrectTrue(Member member);

    List<QuizSolved> findAllByMemberAndWordQuizNotNull(Member member);

    boolean existsByImageQuizAndMemberAndIsCorrect(ImageQuiz imageQuiz, Member member, boolean isCorrect);

    boolean existsByWordQuizAndMemberAndIsCorrect(WordQuiz wordQuiz, Member member, boolean isCorrect);

    List<QuizSolved> findAllByImageQuizAndIsCorrectTrue(ImageQuiz imageQuiz);
}
