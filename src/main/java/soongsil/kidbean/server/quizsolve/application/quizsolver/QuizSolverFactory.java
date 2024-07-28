package soongsil.kidbean.server.quizsolve.application.quizsolver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;
import soongsil.kidbean.server.quizsolve.application.vo.QuizType;

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