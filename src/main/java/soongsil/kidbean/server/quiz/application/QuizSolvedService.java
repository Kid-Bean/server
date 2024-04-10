package soongsil.kidbean.server.quiz.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quiz.application.quizsolver.QuizSolver;
import soongsil.kidbean.server.quiz.application.quizsolver.QuizSolverFactory;
import soongsil.kidbean.server.quiz.application.vo.QuizType;
import soongsil.kidbean.server.quiz.dto.request.QuizSolvedRequest;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class QuizSolvedService {

    private final QuizSolverFactory quizSolverFactory;

    /**
     * 문제를 풀어서 얻은 점수를 return 각각의 문제들은 QuizSolved에 정답을 표기하여 저장
     *
     * @param quizSolvedRequestList 이미지 퀴즈 풀기 요청 목록
     * @param member                푼 멤버
     * @return Long 총 점수
     */
    public Long solveQuizzes(List<QuizSolvedRequest> quizSolvedRequestList, Member member, QuizType type) {

        QuizSolver solver = quizSolverFactory.getSolver(type);

        Long score = quizSolvedRequestList.stream()
                .map(quizSolvedRequest -> solver.solveQuiz(quizSolvedRequest, member))
                .reduce(0L, Long::sum);

        member.updateScore(member.getScore() + score);

        return score;
    }
}
