package soongsil.kidbean.server.summary.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quiz.application.vo.QuizType;
import soongsil.kidbean.server.quiz.domain.ImageQuiz;
import soongsil.kidbean.server.quiz.domain.QuizSolved;
import soongsil.kidbean.server.quiz.domain.type.QuizCategory;
import soongsil.kidbean.server.summary.domain.ImageQuizScore;
import soongsil.kidbean.server.summary.repository.ImageQuizScoreRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageQuizScoreService {

    private final ImageQuizScoreRepository imageQuizScoreRepository;

    @Transactional
    public void addQuizScore(Member member, QuizSolved quizSolved, int addScore, boolean isExist,
                                  QuizType quizType) {
        QuizCategory quizCategory;
        if (quizSolved.isImageQuiz()) {
            quizCategory = quizSolved.getImageQuiz().getQuizCategory();
        } else {
            /*quizCategory = quizSolved.getWordQuiz().getQuizCategory();*/
            //TODO WordQuizCategory 생기면 추가
            quizCategory = quizSolved.getImageQuiz().getQuizCategory();
        }

        ImageQuizScore imageQuizScore = imageQuizScoreRepository.findByMemberAndQuizCategoryAndQuizType(member,
                        quizCategory, quizType)
                .orElseGet(() -> imageQuizScoreRepository.save(
                        ImageQuizScore.makeInitImageQuizScore(member, quizCategory, quizType)));

        ImageQuizScore updateImageQuizScore = imageQuizScore.addScore(addScore)
                .addCount(isExist);

        imageQuizScoreRepository.save(updateImageQuizScore);
    }
}
