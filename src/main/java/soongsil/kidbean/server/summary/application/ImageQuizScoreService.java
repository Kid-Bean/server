package soongsil.kidbean.server.summary.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quiz.application.vo.QuizType;
import soongsil.kidbean.server.quiz.domain.ImageQuiz;
import soongsil.kidbean.server.summary.domain.ImageQuizScore;
import soongsil.kidbean.server.summary.repository.ImageQuizScoreRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageQuizScoreService {

    private final ImageQuizScoreRepository imageQuizScoreRepository;

    @Transactional
    public void addImageQuizScore(Member member, ImageQuiz imageQuiz, int addScore, boolean isExist, QuizType quizType) {
        ImageQuizScore imageQuizScore = imageQuizScoreRepository.findByMemberAndQuizCategoryAndQuizType(member, imageQuiz.getQuizCategory(), quizType)
                .orElseGet(() -> imageQuizScoreRepository.save(ImageQuizScore.makeInitImageQuizScore(member, imageQuiz.getQuizCategory(), quizType)));

        ImageQuizScore updateImageQuizScore = imageQuizScore.addScore(addScore)
                .addCount(isExist);

        imageQuizScoreRepository.save(updateImageQuizScore);
    }
}
