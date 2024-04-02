package soongsil.kidbean.server.quiz.application;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quiz.domain.ImageQuiz;
import soongsil.kidbean.server.quiz.domain.ImageQuizSolved;
import soongsil.kidbean.server.quiz.domain.type.Level;
import soongsil.kidbean.server.quiz.dto.request.ImageQuizSolvedRequest;
import soongsil.kidbean.server.quiz.exception.ImageQuizNotFoundException;
import soongsil.kidbean.server.quiz.exception.ImageQuizSolvedNotFoundException;
import soongsil.kidbean.server.quiz.repository.ImageQuizRepository;
import soongsil.kidbean.server.quiz.repository.ImageQuizSolvedRepository;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ImageQuizSolvedService {

    private final ImageQuizSolvedRepository imageQuizSolvedRepository;
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
     * @param imageQuizSolved 이미지 퀴즈(푼 것)
     * @param member          푼 멤버
     * @return Long 점수
     */
    private Long processImageQuizSolved(ImageQuizSolvedRequest imageQuizSolved, Member member) {
        ImageQuiz imageQuiz = imageQuizRepository.findById(imageQuizSolved.imageQuizId())
                .orElseThrow(ImageQuizNotFoundException::new);

        if (imageQuizSolvedExists(imageQuizSolved, member)) {
            return updateExistingSolvedImageQuiz(imageQuizSolved, imageQuiz, member);
        } else {
            return enrollNewSolvedImageQuiz(imageQuizSolved, imageQuiz, member);
        }
    }

    /**
     * 기존에 푼 ImageQuiz인지 확인
     *
     * @param imageQuizSolved 이미지 퀴즈(푼 것)
     * @return Boolean
     */
    private Boolean imageQuizSolvedExists(ImageQuizSolvedRequest imageQuizSolved, Member member) {
        return imageQuizSolvedRepository.existsImageQuizSolvedByImageQuiz_QuizIdAndMember(
                imageQuizSolved.imageQuizId(), member);
    }

    /**
     * 기존에 풀지 않은 문제의 경우 맞춘 경우 점수 +, 틀린 경우 X SolvedImageQuiz 로 등록
     *
     * @param imageQuizSolvedRequest 이미지 퀴즈 요청
     * @param imageQuiz              이미지 퀴즈
     * @param member                 푼 멤버
     * @return Long 점수
     */
    private Long enrollNewSolvedImageQuiz(ImageQuizSolvedRequest imageQuizSolvedRequest,
                                          ImageQuiz imageQuiz,
                                          Member member) {
        ImageQuizSolved newImageQuizSolved = imageQuizSolvedRequest.toImageQuizSolved(imageQuiz, member);
        newImageQuizSolved.setAnswerIsCorrect(imageQuiz.getAnswer().equals(imageQuizSolvedRequest.answer()));

        imageQuizSolvedRepository.save(newImageQuizSolved);

        return getPoint(imageQuiz, newImageQuizSolved);
    }

    /**
     * 기존에 푼 문제의 경우 맞춘 경우 점수 +, 틀린 경우 X 맞춘 경우만 isCorrect 수정
     *
     * @param imageQuizSolvedRequest 이미지 퀴즈 요청
     * @param imageQuiz              이미지 퀴즈
     * @return Long 점수
     */
    private Long updateExistingSolvedImageQuiz(ImageQuizSolvedRequest imageQuizSolvedRequest,
                                               ImageQuiz imageQuiz,
                                               Member member) {
        ImageQuizSolved imageQuizSolved =
                imageQuizSolvedRepository.findByImageQuizAndMember(imageQuiz, member)
                        .orElseThrow(ImageQuizSolvedNotFoundException::new);
        boolean isCorrect = Objects.equals(imageQuiz.getAnswer(), imageQuizSolvedRequest.answer());

        //이전에 오답이었고 현재 정답인 경우
        if (!imageQuizSolved.getIsCorrect() && isCorrect) {
            imageQuizSolved.setAnswerIsCorrect(true);
            return getPoint(imageQuiz, imageQuizSolved);
        } else {    //이전에 정답인 경우 or 둘 다 오답인 경우
            return 0L;
        }
    }

    /**
     * 문제의 정답, 난이도에 따른 점수 return
     *
     * @param imageQuiz          이미지 퀴즈
     * @param newImageQuizSolved 새로운 이미지 퀴즈(푼 것)
     * @return Long 점수
     */
    private static Long getPoint(ImageQuiz imageQuiz, ImageQuizSolved newImageQuizSolved) {
        if (newImageQuizSolved.getIsCorrect()) {
            return Level.getPoint(imageQuiz.getLevel());
        } else {
            return 0L;
        }
    }
}
