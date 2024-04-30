package soongsil.kidbean.server.summary.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quiz.application.vo.QuizType;
import soongsil.kidbean.server.quiz.domain.QuizSolved;
import soongsil.kidbean.server.quiz.domain.type.QuizCategory;
import soongsil.kidbean.server.summary.domain.QuizScore;
import soongsil.kidbean.server.summary.repository.QuizScoreRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuizScoreService {

    private final QuizScoreRepository quizScoreRepository;

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

        QuizScore quizScore = quizScoreRepository.findByMemberAndQuizCategoryAndQuizType(member,
                        quizCategory, quizType)
                .orElseGet(() -> quizScoreRepository.save(
                        QuizScore.makeInitQuizScore(member, quizCategory, quizType)));

        QuizScore updateQuizScore = quizScore.addScore(addScore)
                .addCount(isExist);

        quizScoreRepository.save(updateQuizScore);
    }
}
