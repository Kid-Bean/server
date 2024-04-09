package soongsil.kidbean.server.quiz.application.quizsolver;

import static soongsil.kidbean.server.quiz.exception.errorcode.QuizErrorCode.IMAGE_QUIZ_SOLVED_NOT_FOUND;
import static soongsil.kidbean.server.quiz.exception.errorcode.QuizErrorCode.WORD_QUIZ_NOT_FOUND;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quiz.domain.QuizSolved;
import soongsil.kidbean.server.quiz.domain.WordQuiz;
import soongsil.kidbean.server.quiz.domain.type.Level;
import soongsil.kidbean.server.quiz.dto.request.QuizSolvedRequest;
import soongsil.kidbean.server.quiz.exception.QuizSolvedNotFoundException;
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
    public Long solveQuiz(QuizSolvedRequest solvedRequest, Member member) {

        WordQuiz wordQuiz = wordQuizRepository.findById(solvedRequest.quizId())
                .orElseThrow(() -> new WordQuizNotFoundException(WORD_QUIZ_NOT_FOUND));
        QuizSolved wordQuizSolved = solvedRequest.toQuizSolved(wordQuiz, member);

        if (wordQuizSolvedExists(wordQuiz, member)) {
            return updateExistingSolvedWordQuiz(wordQuizSolved, wordQuiz);
        } else {
            return enrollNewSolvedWordQuiz(wordQuizSolved, wordQuiz);
        }
    }

    /**
     * 기존에 푼 WordQuiz인지 확인
     *
     * @param wordQuiz 단어 퀴즈(푼 것)
     * @param member   단어 퀴즈를 푼 멤버
     * @return Boolean
     */
    private Boolean wordQuizSolvedExists(WordQuiz wordQuiz, Member member) {
        return quizSolvedRepository.existsByWordQuizAndMember(wordQuiz, member);
    }

    /**
     * 기존에 풀지 않은 문제의 경우 맞춘 경우 점수 +, 틀린 경우 X SolvedImageQuiz 로 등록
     *
     * @param newQuizSolved 단어 퀴즈 요청
     * @param wordQuiz      단어 퀴즈
     * @return Long 점수
     */
    private Long enrollNewSolvedWordQuiz(QuizSolved newQuizSolved, WordQuiz wordQuiz) {

        newQuizSolved.setAnswerIsCorrect(newQuizSolved.getReply().contains(wordQuiz.getAnswer()));
        quizSolvedRepository.save(newQuizSolved);

        return getPoint(wordQuiz, newQuizSolved);
    }

    /**
     * 기존에 푼 문제의 경우 맞춘 경우 점수 +, 틀린 경우 X 맞춘 경우만 isCorrect 수정
     *
     * @param quizSolved 단어 퀴즈 요청
     * @param wordQuiz   단어 퀴즈
     * @return Long 점수
     */
    private Long updateExistingSolvedWordQuiz(QuizSolved quizSolved, WordQuiz wordQuiz) {

        Member member = quizSolved.getMember();
        //이전에 푼 동일한 문제
        QuizSolved wordQuizSolvedEx = quizSolvedRepository.findByWordQuizAndMember(wordQuiz, member)
                .orElseThrow(() -> new QuizSolvedNotFoundException(IMAGE_QUIZ_SOLVED_NOT_FOUND));

        //정답을 포함하고 있는지
        boolean isCorrect = quizSolved.getReply().contains(wordQuiz.getAnswer());

        //이전에 오답이었고 현재 정답인 경우
        if (!wordQuizSolvedEx.getIsCorrect() && isCorrect) {
            wordQuizSolvedEx.setAnswerIsCorrect(true);
            return getPoint(wordQuiz, wordQuizSolvedEx);
        } else {    //이전에 정답인 경우 or 둘 다 오답인 경우
            return 0L;
        }
    }

    /**
     * 문제의 정답, 난이도에 따른 점수 return
     *
     * @param wordQuiz      단어 퀴즈
     * @param newQuizSolved 새로운 단어 퀴즈(푼 것)
     * @return Long 점수
     */
    private static Long getPoint(WordQuiz wordQuiz, QuizSolved newQuizSolved) {
        return newQuizSolved.getIsCorrect() ? Level.getPoint(wordQuiz.getLevel()) : 0L;
    }
}
