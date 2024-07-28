package soongsil.kidbean.server.quizsolve.application.quizscorer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quizsolve.application.quizsolver.dto.SolvedQuizInfo;
import soongsil.kidbean.server.summary.domain.QuizScore;
import soongsil.kidbean.server.summary.repository.QuizScoreRepository;

@Service
@RequiredArgsConstructor
public class ImageQuizScorer implements QuizScorer {

    private final QuizScoreRepository quizScoreRepository;

    @Transactional
    @Override
    public Long addPerQuizScore(SolvedQuizInfo solvedQuizInfo, Member member) {
        QuizScore quizScore = quizScoreRepository.findByMemberAndQuizCategory(member, solvedQuizInfo.category())
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버의 퀴즈 카테고리가 존재하지 않습니다."));

        quizScore.addScore(solvedQuizInfo.score());

        return solvedQuizInfo.score();
    }
}
