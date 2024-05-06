package soongsil.kidbean.server.quiz.application.quizsolver;

import static soongsil.kidbean.server.quiz.exception.errorcode.QuizErrorCode.WORD_QUIZ_NOT_FOUND;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quiz.application.quizsolver.dto.SolvedQuizInfo;
import soongsil.kidbean.server.quiz.domain.QuizSolved;
import soongsil.kidbean.server.quiz.domain.WordQuiz;
import soongsil.kidbean.server.quiz.domain.type.Level;
import soongsil.kidbean.server.quiz.dto.request.QuizSolvedRequest;
import soongsil.kidbean.server.quiz.exception.WordQuizNotFoundException;
import soongsil.kidbean.server.quiz.repository.QuizSolvedRepository;
import soongsil.kidbean.server.quiz.repository.WordQuizRepository;

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

        if (wordQuizSolvedExists(wordQuiz, member)) {
            return solveExistingWordQuizSolved(wordQuizSolved, wordQuiz);
        } else {
            return SolveNewWordQuiz(wordQuizSolved, wordQuiz);
        }
    }

    private Boolean wordQuizSolvedExists(WordQuiz wordQuiz, Member member) {
        return quizSolvedRepository.existsByWordQuizAndMember(wordQuiz, member);
    }

    private SolvedQuizInfo SolveNewWordQuiz(QuizSolved newQuizSolved, WordQuiz wordQuiz) {

        newQuizSolved.setAnswerIsCorrect(newQuizSolved.getReply().contains(wordQuiz.getAnswer()));
        quizSolvedRepository.save(newQuizSolved);

        return newQuizSolved.getIsCorrect() ? new SolvedQuizInfo(wordQuiz.getQuizCategory(),
                getPoint(wordQuiz.getLevel()), false) : new SolvedQuizInfo(wordQuiz.getQuizCategory(), 0L, false);
    }

    private SolvedQuizInfo solveExistingWordQuizSolved(QuizSolved newQuizSolved, WordQuiz wordQuiz) {

        Member member = newQuizSolved.getMember();
        //이전에 맞았는지
        boolean exCorrect = quizSolvedRepository.existsByWordQuizAndMemberAndIsCorrect(wordQuiz, member, true);
        //정답을 포함하고 있는지
        boolean isCorrect = newQuizSolved.getReply().contains(wordQuiz.getAnswer());

        //푼 QuizSolved 등록
        newQuizSolved.setAnswerIsCorrect(isCorrect);
        quizSolvedRepository.save(newQuizSolved);

        //이전에 오답이었고 현재 정답인 경우
        if (!exCorrect && isCorrect) {
            return new SolvedQuizInfo(wordQuiz.getQuizCategory(), getPoint(wordQuiz.getLevel()), true);
        } else {    //이전에 정답인 경우 or 둘 다 오답인 경우
            return new SolvedQuizInfo(wordQuiz.getQuizCategory(), 0L, true);
        }
    }

    private static Long getPoint(Level level) {
        return Level.getPoint(level);
    }
}
