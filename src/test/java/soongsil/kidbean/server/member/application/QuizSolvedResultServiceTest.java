package soongsil.kidbean.server.member.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;
import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER1;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soongsil.kidbean.server.member.dto.response.SolvedImageListResponse;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.quizsolve.application.AnalyzeMorphemeService;
import soongsil.kidbean.server.answerquiz.application.OpenApiService;
import soongsil.kidbean.server.quizsolve.fixture.QuizSolvedFixture;
import soongsil.kidbean.server.quizsolve.repository.AnswerQuizSolvedRepository;
import soongsil.kidbean.server.quizsolve.repository.QuizSolvedRepository;
import soongsil.kidbean.server.quizsolve.repository.UseWordRepository;
import soongsil.kidbean.server.wordquiz.repository.WordRepository;
import soongsil.kidbean.server.summary.repository.AverageScoreRepository;
import soongsil.kidbean.server.summary.repository.QuizScoreRepository;

@ExtendWith(MockitoExtension.class)
class QuizSolvedResultServiceTest {

    @InjectMocks
    private QuizSolvedResultService quizSolvedResultService;

    @Mock
    private QuizSolvedRepository quizSolvedRepository;
    @Mock
    private AnswerQuizSolvedRepository answerQuizSolvedRepository;
    @Mock
    private WordRepository wordRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private QuizScoreRepository imageQuizScoreRepository;
    @Mock
    private AverageScoreRepository averageScoreRepository;
    @Mock
    private UseWordRepository useWordRepository;
    @Mock
    private AnalyzeMorphemeService analyzeMorphemeService;
    @Mock
    private OpenApiService openApiService;


    @Test
    @DisplayName("이미지 퀴즈 리스트 반환 Service Test")
    void findSolvedImageTest() {
        //given
        given(memberRepository.findById(eq(MEMBER1.getMemberId())))
                .willReturn(Optional.of(MEMBER1));
        given(quizSolvedRepository.findAllByMemberAndIsCorrectAndImageQuizIsNotNull(eq(MEMBER1), eq(true)))
                .willReturn(List.of(QuizSolvedFixture.IMAGE_QUIZ_SOLVED_ANIMAL_TRUE));

        //when
        SolvedImageListResponse solvedImageListResponse =
                quizSolvedResultService.findSolvedImage(MEMBER1.getMemberId(), true);

        //then
        assertThat(solvedImageListResponse.solvedList().size()).isEqualTo(1);
    }
}