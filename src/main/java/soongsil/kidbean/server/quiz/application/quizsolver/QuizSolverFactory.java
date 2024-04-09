package soongsil.kidbean.server.quiz.application.quizsolver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.quiz.application.vo.QuizType;

@Transactional
@Service
public class QuizSolverFactory {

    private final Map<QuizType, QuizSolver> solverMap = new ConcurrentHashMap<>();

    public QuizSolverFactory(ImageQuizSolver imageQuizSolver, WordQuizSolver wordQuizSolver) {
        solverMap.put(QuizType.IMAGE_QUIZ, imageQuizSolver);
        solverMap.put(QuizType.WORD_QUIZ, wordQuizSolver);
    }

    public QuizSolver getSolver(QuizType type) {
        return solverMap.get(type);
    }
}