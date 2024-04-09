package soongsil.kidbean.server.quiz.application;

import static soongsil.kidbean.server.quiz.exception.errorcode.QuizErrorCode.IMAGE_QUIZ_NOT_FOUND;
import static soongsil.kidbean.server.quiz.exception.errorcode.QuizErrorCode.IMAGE_QUIZ_SOLVED_NOT_FOUND;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quiz.domain.ImageQuiz;
import soongsil.kidbean.server.quiz.domain.QuizSolved;
import soongsil.kidbean.server.quiz.domain.type.Level;
import soongsil.kidbean.server.quiz.dto.request.ImageQuizSolvedRequest;
import soongsil.kidbean.server.quiz.exception.ImageQuizNotFoundException;
import soongsil.kidbean.server.quiz.exception.ImageQuizSolvedNotFoundException;
import soongsil.kidbean.server.quiz.repository.ImageQuizRepository;
import soongsil.kidbean.server.quiz.repository.QuizSolvedRepository;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class QuizSolvedService {

    private final QuizSolvedRepository quizSolvedRepository;
    private final ImageQuizRepository imageQuizRepository;

    /**
     * 문제를 풀어서 얻은 점수를 return 각각의 문제들은 ImageQuizSolved에 정답을 표기하여 저장
     *
     * @param imageQuizSolvedRequestList 이미지 퀴즈 풀기 요청 목록
     * @param member                     푼 멤버
     * @return Long 총 점수
     */
    public Long solveImageQuizzes(List<ImageQuizSolvedRequest> imageQuizSolvedRequestList, Member member) {
        return imageQuizSolvedRequestList.stream()
                .map(imageQuizSolved -> processImageQuizSolved(imageQuizSolved, member))
                .reduce(0L, Long::sum);
    }

    /**
     * 기존에 풀었던 문제인지에 따라 다르게 처리
     *
     * @param solvedRequest ImageQuizSolved DTO
     * @param member        푼 멤버
     * @return Long 점수
     */
    private Long processImageQuizSolved(ImageQuizSolvedRequest solvedRequest, Member member) {

        ImageQuiz imageQuiz = imageQuizRepository.findById(solvedRequest.imageQuizId())
                .orElseThrow(() -> new ImageQuizNotFoundException(IMAGE_QUIZ_NOT_FOUND));
        QuizSolved imageQuizSolved = solvedRequest.toQuizSolved(imageQuiz, member);

        if (imageQuizSolvedExists(imageQuiz, member)) {
            return updateExistingSolvedImageQuiz(imageQuizSolved, imageQuiz);
        } else {
            return enrollNewSolvedImageQuiz(imageQuizSolved, imageQuiz);
        }
    }

    /**
     * 기존에 푼 ImageQuiz인지 확인
     *
     * @param imageQuiz 이미지 퀴즈(푼 것)
     * @param member    이미지 퀴즈를 푼 멤버
     * @return Boolean
     */
    private Boolean imageQuizSolvedExists(ImageQuiz imageQuiz, Member member) {
        return quizSolvedRepository.existsImageQuizSolvedByImageQuizAndMember(imageQuiz, member);
    }

    /**
     * 기존에 풀지 않은 문제의 경우 맞춘 경우 점수 +, 틀린 경우 X SolvedImageQuiz 로 등록
     *
     * @param newQuizSolved 이미지 퀴즈 요청
     * @param imageQuiz     이미지 퀴즈
     * @return Long 점수
     */
    private Long enrollNewSolvedImageQuiz(QuizSolved newQuizSolved, ImageQuiz imageQuiz) {

        newQuizSolved.setAnswerIsCorrect(newQuizSolved.getReply().contains(imageQuiz.getAnswer()));
        quizSolvedRepository.save(newQuizSolved);

        return getPoint(imageQuiz, newQuizSolved);
    }

    /**
     * 기존에 푼 문제의 경우 맞춘 경우 점수 +, 틀린 경우 X 맞춘 경우만 isCorrect 수정
     *
     * @param quizSolved 이미지 퀴즈 요청
     * @param imageQuiz  이미지 퀴즈
     * @return Long 점수
     */
    private Long updateExistingSolvedImageQuiz(QuizSolved quizSolved, ImageQuiz imageQuiz) {

        Member member = quizSolved.getMember();
        //이전에 푼 동일한 문제
        QuizSolved imageQuizSolvedEx = quizSolvedRepository.findByImageQuizAndMember(imageQuiz, member)
                .orElseThrow(() -> new ImageQuizSolvedNotFoundException(IMAGE_QUIZ_SOLVED_NOT_FOUND));

        //정답을 포함하고 있는지
        boolean isCorrect = quizSolved.getReply().contains(imageQuiz.getAnswer());

        //이전에 오답이었고 현재 정답인 경우
        if (!imageQuizSolvedEx.getIsCorrect() && isCorrect) {
            imageQuizSolvedEx.setAnswerIsCorrect(true);
            return getPoint(imageQuiz, imageQuizSolvedEx);
        } else {    //이전에 정답인 경우 or 둘 다 오답인 경우
            return 0L;
        }
    }

    /**
     * 문제의 정답, 난이도에 따른 점수 return
     *
     * @param imageQuiz     이미지 퀴즈
     * @param newQuizSolved 새로운 이미지 퀴즈(푼 것)
     * @return Long 점수
     */
    private static Long getPoint(ImageQuiz imageQuiz, QuizSolved newQuizSolved) {
        return newQuizSolved.getIsCorrect() ? Level.getPoint(imageQuiz.getLevel()) : 0L;
    }
}