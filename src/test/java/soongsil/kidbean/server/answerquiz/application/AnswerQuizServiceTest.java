package soongsil.kidbean.server.answerquiz.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER1;
import static soongsil.kidbean.server.answerquiz.fixture.AnswerQuizFixture.ANSWER_QUIZ;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.domain.type.Role;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.quizsolve.application.vo.OpenApiResponse;
import soongsil.kidbean.server.answerquiz.domain.AnswerQuiz;
import soongsil.kidbean.server.answerquiz.dto.request.AnswerQuizSolvedRequest;
import soongsil.kidbean.server.answerquiz.dto.request.AnswerQuizUpdateRequest;
import soongsil.kidbean.server.answerquiz.dto.request.AnswerQuizUploadRequest;
import soongsil.kidbean.server.answerquiz.dto.response.AnswerQuizMemberDetailResponse;
import soongsil.kidbean.server.answerquiz.dto.response.AnswerQuizMemberResponse;
import soongsil.kidbean.server.answerquiz.dto.response.AnswerQuizResponse;
import soongsil.kidbean.server.answerquiz.dto.response.AnswerQuizSolveScoreResponse;
import soongsil.kidbean.server.answerquiz.repository.AnswerQuizRepository;
import soongsil.kidbean.server.quizsolve.application.AnswerQuizSolvedService;

@ExtendWith(MockitoExtension.class)
class AnswerQuizServiceTest {

    @Mock
    private AnswerQuizRepository answerQuizRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private OpenApiService openApiService;

    @Mock
    private AnswerQuizSolvedService answerQuizSolvedService;

    @InjectMocks
    private AnswerQuizService answerQuizService;

    @Test
    @DisplayName("랜덤 WordQuiz 선택")
    void selectRandomWordQuiz() {
        //given
        given(memberRepository.findById(MEMBER1.getMemberId()))
                .willReturn(Optional.of(MEMBER1));
        given(answerQuizRepository.countByMemberOrMember_Role(MEMBER1, Role.ADMIN))
                .willReturn(1);
        given(answerQuizRepository.findByMemberOrMember_Role(MEMBER1, Role.ADMIN, PageRequest.of(0, 1)))
                .willReturn(new PageImpl<>(List.of(ANSWER_QUIZ)));

        //when
        AnswerQuizResponse answerQuizResponse = answerQuizService.selectRandomAnswerQuiz(MEMBER1.getMemberId());

        //then
        assertThat(answerQuizResponse.question()).isEqualTo(ANSWER_QUIZ.getQuestion());
    }

    @Test
    @DisplayName("AnswerQuiz 풀기 요청 테스트")
    void submitAnswerQuiz() {
        //given
        String submitAnswer = "제출된 정답";
        String fileName = "test.png";
        String contentType = "image/png";
        long expectedScore = 3L;

        MockMultipartFile multipartFile = new MockMultipartFile("test", fileName, contentType, "test".getBytes());
        OpenApiResponse openApiResponse = new OpenApiResponse(null, null);
        AnswerQuizSolvedRequest answerQuizSolvedRequest =
                new AnswerQuizSolvedRequest(ANSWER_QUIZ.getQuizId(), submitAnswer);

        given(memberRepository.findById(anyLong())).willReturn(Optional.of(MEMBER1));
        given(answerQuizRepository.findById(anyLong())).willReturn(Optional.of(ANSWER_QUIZ));
        given(openApiService.analyzeAnswer(submitAnswer)).willReturn(openApiResponse);

        //when
        AnswerQuizSolveScoreResponse response = answerQuizService.submitAnswerQuiz(
                answerQuizSolvedRequest, multipartFile, MEMBER1.getMemberId());

        //then
        verify(answerQuizSolvedService, times(1))
                .enrollNewAnswerQuizSolved(any(AnswerQuiz.class), anyString(), any(Member.class),
                        eq(openApiResponse), eq(multipartFile));

        assertThat(response.score()).isEqualTo(expectedScore);
    }

    @Test
    @DisplayName("AnswerQuiz 상세 정보 가져오기")
    void getAnswerQuizById() {
        // given
        given(answerQuizRepository.findByQuizIdAndMember_MemberId(anyLong(), anyLong())).willReturn(
                Optional.of(ANSWER_QUIZ));

        // when
        AnswerQuizMemberDetailResponse response = answerQuizService.getAnswerQuizById(MEMBER1.getMemberId(),
                ANSWER_QUIZ.getQuizId());

        // then
        assertThat(response.question()).isEqualTo(ANSWER_QUIZ.getQuestion());
    }

    @Test
    @DisplayName("추가한 AnswerQuiz 리스트 가져오기")
    void getAllAnswerQuizByMember() {
        // given
        given(answerQuizRepository.findAllByMember_MemberId(anyLong())).willReturn(List.of(ANSWER_QUIZ));

        // when
        List<AnswerQuizMemberResponse> response = answerQuizService.getAllAnswerQuizByMember(MEMBER1.getMemberId());

        // then
        assertThat(response).hasSize(1);
    }

    @Test
    @DisplayName("AnswerQuiz 등록하기")
    void uploadAnswerQuiz() {
        // given
        AnswerQuizUploadRequest request = new AnswerQuizUploadRequest(ANSWER_QUIZ.getTitle(),
                ANSWER_QUIZ.getQuestion());

        given(memberRepository.findById(MEMBER1.getMemberId())).willReturn(Optional.of(MEMBER1));

        // when
        answerQuizService.uploadAnswerQuiz(request, MEMBER1.getMemberId());

        // then
        verify(answerQuizRepository, times(1)).save(any(AnswerQuiz.class));
    }

    @Test
    @DisplayName("AnswerQuiz 수정하기")
    void updateAnswerQuiz() {
        // given
        AnswerQuizUpdateRequest request = new AnswerQuizUpdateRequest(ANSWER_QUIZ.getTitle(),
                ANSWER_QUIZ.getQuestion());

        given(answerQuizRepository.findById(ANSWER_QUIZ.getQuizId())).willReturn(Optional.of(ANSWER_QUIZ));

        // when
        answerQuizService.updateAnswerQuiz(request, ANSWER_QUIZ.getQuizId());

        // then
        assertThat(ANSWER_QUIZ.getTitle()).isEqualTo(request.title());
    }

    @Test
    @DisplayName("AnswerQuiz 삭제하기")
    void deleteAnswerQuiz() {
        // given
        given(answerQuizRepository.findById(ANSWER_QUIZ.getQuizId())).willReturn(Optional.of(ANSWER_QUIZ));

        // when
        answerQuizService.deleteAnswerQuiz(ANSWER_QUIZ.getQuizId());

        // then
        verify(answerQuizRepository, times(1)).delete(any(AnswerQuiz.class));
    }
}