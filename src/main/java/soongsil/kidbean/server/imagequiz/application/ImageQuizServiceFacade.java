package soongsil.kidbean.server.imagequiz.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import soongsil.kidbean.server.imagequiz.dto.response.ImageQuizSolveScoreResponse;
import soongsil.kidbean.server.quizsolve.dto.request.QuizSolvedListRequest;

@Slf4j
@RequiredArgsConstructor
@Component
public class ImageQuizServiceFacade {

    private final ImageQuizService imageQuizService;
    private final MemberScoreUpdateStrategy memberScoreUpdateStrategy;

    public ImageQuizSolveScoreResponse solveImageQuizzes(QuizSolvedListRequest request, Long memberId) {
        ImageQuizSolveScoreResponse score =
                imageQuizService.solveImageQuizzes(request.quizSolvedRequestList(), memberId);

        if (score.score() != 0) {
            memberScoreUpdateStrategy.updateUserScore(memberId, score.score());
        }

        return score;
    }
}
