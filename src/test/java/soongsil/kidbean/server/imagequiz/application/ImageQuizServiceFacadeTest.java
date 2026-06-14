package soongsil.kidbean.server.imagequiz.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soongsil.kidbean.server.imagequiz.dto.response.ImageQuizSolveScoreResponse;
import soongsil.kidbean.server.quizsolve.dto.request.QuizSolvedListRequest;

@ExtendWith(MockitoExtension.class)
class ImageQuizServiceFacadeTest {

    @InjectMocks
    private ImageQuizServiceFacade imageQuizServiceFacade;

    @Mock
    private ImageQuizService imageQuizService;

    @Mock
    private MemberScoreUpdateStrategy memberScoreUpdateStrategy;

    @Test
    @DisplayName("점수가 0이 아니면 회원 점수를 갱신한다")
    void solveImageQuizzes_updatesScore() {
        // given
        QuizSolvedListRequest request = new QuizSolvedListRequest(List.of());
        ImageQuizSolveScoreResponse response = ImageQuizSolveScoreResponse.scoreFrom(20L);
        given(imageQuizService.solveImageQuizzes(request.quizSolvedRequestList(), 1L))
                .willReturn(response);

        // when
        ImageQuizSolveScoreResponse result = imageQuizServiceFacade.solveImageQuizzes(request, 1L);

        // then
        assertThat(result.score()).isEqualTo(20L);
        verify(memberScoreUpdateStrategy, times(1)).updateUserScore(1L, 20L);
    }

    @Test
    @DisplayName("점수가 0이면 회원 점수를 갱신하지 않는다")
    void solveImageQuizzes_zeroScore() {
        // given
        QuizSolvedListRequest request = new QuizSolvedListRequest(List.of());
        ImageQuizSolveScoreResponse response = ImageQuizSolveScoreResponse.scoreFrom(0L);
        given(imageQuizService.solveImageQuizzes(request.quizSolvedRequestList(), 1L))
                .willReturn(response);

        // when
        ImageQuizSolveScoreResponse result = imageQuizServiceFacade.solveImageQuizzes(request, 1L);

        // then
        assertThat(result.score()).isEqualTo(0L);
        verify(memberScoreUpdateStrategy, never()).updateUserScore(anyLong(), any());
    }
}
