package soongsil.kidbean.server.quizsolve.application.quizsolver;


import static soongsil.kidbean.server.imagequiz.exception.errorcode.ImageQuizErrorCode.IMAGE_QUIZ_NOT_FOUND;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quizsolve.application.quizsolver.dto.SolvedQuizInfo;
import soongsil.kidbean.server.imagequiz.domain.ImageQuiz;
import soongsil.kidbean.server.quizsolve.domain.QuizSolved;
import soongsil.kidbean.server.quizsolve.domain.type.Level;
import soongsil.kidbean.server.quizsolve.dto.request.QuizSolvedRequest;
import soongsil.kidbean.server.imagequiz.exception.ImageQuizNotFoundException;
import soongsil.kidbean.server.imagequiz.repository.ImageQuizRepository;
import soongsil.kidbean.server.quizsolve.repository.QuizSolvedRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageQuizSolver implements QuizSolver {

    private final ImageQuizRepository imageQuizRepository;
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

        ImageQuiz imageQuiz = imageQuizRepository.findById(solvedRequest.quizId())
                .orElseThrow(() -> new ImageQuizNotFoundException(IMAGE_QUIZ_NOT_FOUND));
        QuizSolved imageQuizSolved = solvedRequest.toQuizSolved(imageQuiz, member);

        return solveNewImageQuiz(imageQuizSolved, imageQuiz);
    }

    private SolvedQuizInfo solveNewImageQuiz(QuizSolved quizSolved, ImageQuiz imageQuiz) {

        QuizSolved newQuizSolved = quizSolvedRepository.save(quizSolved);

        return !newQuizSolved.getIsCorrect() ? new SolvedQuizInfo(imageQuiz.getQuizCategory(), 0L)
                : new SolvedQuizInfo(imageQuiz.getQuizCategory(), Level.getPoint(imageQuiz.getLevel()));
    }
}
