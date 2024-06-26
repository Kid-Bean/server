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

        if (imageQuizSolvedExists(imageQuiz, member)) {
            return solveExistingImageQuizSolved(imageQuizSolved, imageQuiz);
        } else {
            return solveNewImageQuiz(imageQuizSolved, imageQuiz);
        }
    }

    private Boolean imageQuizSolvedExists(ImageQuiz imageQuiz, Member member) {
        return quizSolvedRepository.existsByImageQuizAndMember(imageQuiz, member);
    }

    private SolvedQuizInfo solveNewImageQuiz(QuizSolved newQuizSolved, ImageQuiz imageQuiz) {

        newQuizSolved.setAnswerIsCorrect(newQuizSolved.getReply().contains(imageQuiz.getAnswer()));
        quizSolvedRepository.save(newQuizSolved);

        return newQuizSolved.getIsCorrect() ? new SolvedQuizInfo(imageQuiz.getQuizCategory(),
                getPoint(imageQuiz.getLevel()), false) : new SolvedQuizInfo(imageQuiz.getQuizCategory(), 0L, false);
    }

    private SolvedQuizInfo solveExistingImageQuizSolved(QuizSolved newQuizSolved, ImageQuiz imageQuiz) {

        Member member = newQuizSolved.getMember();
        boolean exCorrect = quizSolvedRepository.existsByImageQuizAndMemberAndIsCorrect(imageQuiz, member, true);
        //이번에 정답을 맞췄는지 확인
        boolean isCorrect = newQuizSolved.getReply().contains(imageQuiz.getAnswer());

        //푼 QuizSolved 등록
        newQuizSolved.setAnswerIsCorrect(isCorrect);
        quizSolvedRepository.save(newQuizSolved);

        //이전에 오답이었고 현재 정답인 경우
        if (!exCorrect && isCorrect) {
            return new SolvedQuizInfo(imageQuiz.getQuizCategory(), getPoint(imageQuiz.getLevel()), true);
        } else {    //이전에 정답인 경우 or 둘 다 오답인 경우
            return new SolvedQuizInfo(imageQuiz.getQuizCategory(), 0L, true);
        }
    }

    private static Long getPoint(Level level) {
        return Level.getPoint(level);
    }
}
