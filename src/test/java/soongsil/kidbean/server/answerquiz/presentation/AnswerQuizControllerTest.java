package soongsil.kidbean.server.answerquiz.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.multipart.MultipartFile;
import soongsil.kidbean.server.global.application.config.CommonControllerTest;
import soongsil.kidbean.server.answerquiz.application.AnswerQuizService;
import soongsil.kidbean.server.answerquiz.dto.request.AnswerQuizSolvedRequest;
import soongsil.kidbean.server.answerquiz.dto.request.AnswerQuizUpdateRequest;
import soongsil.kidbean.server.answerquiz.dto.request.AnswerQuizUploadRequest;
import soongsil.kidbean.server.answerquiz.dto.response.AnswerQuizMemberDetailResponse;
import soongsil.kidbean.server.answerquiz.dto.response.AnswerQuizMemberResponse;
import soongsil.kidbean.server.answerquiz.dto.response.AnswerQuizResponse;
import soongsil.kidbean.server.answerquiz.dto.response.AnswerQuizSolveScoreResponse;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER1;
import static soongsil.kidbean.server.answerquiz.fixture.AnswerQuizFixture.ANSWER_QUIZ;

@WebMvcTest(AnswerQuizController.class)
class AnswerQuizControllerTest extends CommonControllerTest {

    @MockBean
    AnswerQuizService answerQuizService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("랜덤 AnswerQuiz 생성 요청")
    void getRandomAnswerQuiz() throws Exception {
        //given
        Long memberId = MEMBER1.getMemberId();
        AnswerQuizResponse answerQuizResponse = AnswerQuizResponse.from(ANSWER_QUIZ);
        given(answerQuizService.selectRandomAnswerQuiz(memberId))
                .willReturn(answerQuizResponse);

        //when
        ResultActions resultActions = mockMvc.perform(get("/quiz/answer/solve"))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.results.question").value(ANSWER_QUIZ.getQuestion()));
    }

    @Test
    @DisplayName("AnswerQuiz 풀기 요청")
    void solveAnswerQuiz() throws Exception {
        //given
        long returnScore = 3L;

        AnswerQuizSolvedRequest request = new AnswerQuizSolvedRequest(ANSWER_QUIZ.getQuizId(), "sample answer");
        AnswerQuizSolveScoreResponse response = new AnswerQuizSolveScoreResponse(returnScore);

        given(answerQuizService.submitAnswerQuiz(
                eq(request), any(MultipartFile.class), eq(MEMBER1.getMemberId()))).willReturn(response);

        MockMultipartFile file = new MockMultipartFile("record", "filename.txt", "text/plain", "some xml".getBytes());
        MockMultipartFile jsonFile = new MockMultipartFile(
                "answerQuizSolvedRequest", "", "application/json", objectMapper.writeValueAsString(request).getBytes());

        //when
        ResultActions resultActions = mockMvc.perform(multipart("/quiz/answer/solve")
                        .file(file)
                        .file(jsonFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.results.score").value(returnScore));
    }

    @Test
    @DisplayName("추가한 AnswerQuiz 리스트 가져오기")
    void getAllAnswerQuizByMember() throws Exception {
        // given
        List<AnswerQuizMemberResponse> responses = List.of(AnswerQuizMemberResponse.from(ANSWER_QUIZ));

        given(answerQuizService.getAllAnswerQuizByMember(anyLong())).willReturn(responses);

        // when
        ResultActions resultActions = mockMvc.perform(get("/quiz/answer/member"))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.results[0].title").value(ANSWER_QUIZ.getTitle()));
    }

    @Test
    @DisplayName("AnswerQuiz 상세 정보 가져오기")
    void getAnswerQuizById() throws Exception {
        // given
        AnswerQuizMemberDetailResponse response = new AnswerQuizMemberDetailResponse(ANSWER_QUIZ.getTitle(),
                ANSWER_QUIZ.getQuestion());

        given(answerQuizService.getAnswerQuizById(anyLong(), anyLong())).willReturn(response);

        // when
        ResultActions resultActions = mockMvc.perform(get("/quiz/answer/member/1"))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.results.title").value(ANSWER_QUIZ.getTitle()));
    }

    @Test
    @DisplayName("AnswerQuiz 등록하기")
    void uploadAnswerQuiz() throws Exception {
        // given
        AnswerQuizUploadRequest request = new AnswerQuizUploadRequest(anyString(), anyString());

        // when
        ResultActions resultActions = mockMvc.perform(post("/quiz/answer/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("AnswerQuiz 수정하기")
    void updateAnswerQuiz() throws Exception {
        // given
        AnswerQuizUpdateRequest request = new AnswerQuizUpdateRequest(anyString(), anyString());

        // when
        ResultActions resultActions = mockMvc.perform(put("/quiz/answer/member/1")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("AnswerQuiz 삭제하기")
    void deleteAnswerQuiz() throws Exception {
        // given

        // when
        ResultActions resultActions = mockMvc.perform(delete("/quiz/answer/member/1"))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk());
    }
}