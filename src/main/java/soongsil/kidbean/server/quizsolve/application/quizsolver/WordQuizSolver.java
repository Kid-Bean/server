package soongsil.kidbean.server.quizsolve.application.quizsolver;


import static soongsil.kidbean.server.wordquiz.exception.errorcode.WordQuizErrorCode.WORD_QUIZ_NOT_FOUND;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quizsolve.application.quizsolver.dto.SolvedQuizInfo;
import soongsil.kidbean.server.quizsolve.domain.QuizSolved;
import soongsil.kidbean.server.wordquiz.domain.WordQuiz;
import soongsil.kidbean.server.quizsolve.domain.type.Level;
import soongsil.kidbean.server.quizsolve.dto.request.QuizSolvedRequest;
import soongsil.kidbean.server.wordquiz.exception.WordQuizNotFoundException;
import soongsil.kidbean.server.quizsolve.repository.QuizSolvedRepository;
import soongsil.kidbean.server.wordquiz.repository.WordQuizRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class WordQuizSolver implements QuizSolver {

    private final WordQuizRepository wordQuizRepository;
    private final QuizSolvedRepository quizSolvedRepository;

    /**
     * 기존에 풀었던 문제인지에 따라 다르게 처리
     *
     * @param solvedRequest ImageQuizSolved DTO
     * @param member        푼 멤버
     * @return Long 점수
     */
    @Override
    public SolvedQuizInfo solveQuiz(QuizSolvedRequest solvedRequest, Member member) {

        WordQuiz wordQuiz = wordQuizRepository.findById(solvedRequest.quizId())
                .orElseThrow(() -> new WordQuizNotFoundException(WORD_QUIZ_NOT_FOUND));
        QuizSolved wordQuizSolved = solvedRequest.toQuizSolved(wordQuiz, member);

        return solveNewWordQuiz(wordQuizSolved, wordQuiz);
    }

    private SolvedQuizInfo solveNewWordQuiz(QuizSolved quizSolved, WordQuiz wordQuiz) {

        QuizSolved newQuizSolved = quizSolvedRepository.save(quizSolved);

        return !newQuizSolved.getIsCorrect() ? new SolvedQuizInfo(wordQuiz.getQuizCategory(), 0L)
                : new SolvedQuizInfo(wordQuiz.getQuizCategory(), Level.getPoint(wordQuiz.getLevel()));
    }
}
