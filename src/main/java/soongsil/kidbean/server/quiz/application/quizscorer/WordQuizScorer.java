package soongsil.kidbean.server.quiz.application.quizscorer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quiz.application.quizsolver.dto.SolvedQuizInfo;
import soongsil.kidbean.server.summary.domain.QuizScore;
import soongsil.kidbean.server.summary.repository.QuizScoreRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class WordQuizScorer implements QuizScorer{

    private final QuizScoreRepository quizScoreRepository;
    @Override
    public Long addPerQuizScore(SolvedQuizInfo solvedQuizInfo, Member member) {
        QuizScore quizScore = quizScoreRepository.findByMemberAndQuizCategory(member, solvedQuizInfo.category())
                .orElseGet(() -> quizScoreRepository.save(
                        QuizScore.makeInitQuizScore(member, solvedQuizInfo.category())));

        QuizScore updateQuizScore = quizScore.addScore(solvedQuizInfo.score())
                .addCount(solvedQuizInfo.isExist());
        quizScoreRepository.save(updateQuizScore);

        return solvedQuizInfo.score();
    }
}
