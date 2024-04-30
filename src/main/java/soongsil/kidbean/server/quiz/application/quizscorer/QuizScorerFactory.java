package soongsil.kidbean.server.quiz.application.quizscorer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;
import soongsil.kidbean.server.quiz.application.quizsolver.ImageQuizSolver;
import soongsil.kidbean.server.quiz.application.quizsolver.QuizSolver;
import soongsil.kidbean.server.quiz.application.quizsolver.WordQuizSolver;
import soongsil.kidbean.server.quiz.application.vo.QuizType;

@Service
public class QuizScorerFactory {
    private final Map<QuizType, QuizScorer> solverMap = new ConcurrentHashMap<>();

    public QuizScorerFactory(ImageQuizScorer imageQuizScorer, WordQuizScorer wordQuizScorer) {
        solverMap.put(QuizType.IMAGE_QUIZ, imageQuizScorer);
        solverMap.put(QuizType.WORD_QUIZ, wordQuizScorer);
    }

    public QuizScorer getSolver(QuizType type) {
        return solverMap.get(type);
    }
}
