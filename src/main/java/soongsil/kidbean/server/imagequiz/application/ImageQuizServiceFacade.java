package soongsil.kidbean.server.imagequiz.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import soongsil.kidbean.server.imagequiz.dto.response.ImageQuizSolveScoreResponse;
import soongsil.kidbean.server.quizsolve.dto.request.QuizSolvedRequest;

@RequiredArgsConstructor
@Component
public class ImageQuizServiceFacade {

    private final ImageQuizService imageQuizService;
    private final MemberScoreUpdateStrategy memberScoreUpdateStrategy;

    public ImageQuizSolveScoreResponse solveImageQuizzes(
            List<QuizSolvedRequest> quizSolvedRequestList, Long memberId
    ) {
        ImageQuizSolveScoreResponse score =
                imageQuizService.solveImageQuizzes(quizSolvedRequestList, memberId);

        if (score.score() != 0) {
            memberScoreUpdateStrategy.updateUserScore(score.score(), memberId);
        }

        return score;
    }
}
