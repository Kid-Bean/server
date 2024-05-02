package soongsil.kidbean.server.quiz.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER1;
import static soongsil.kidbean.server.quiz.fixture.AnswerQuizFixture.ANSWER_QUIZ;

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
import soongsil.kidbean.server.quiz.application.AnswerQuizService;
import soongsil.kidbean.server.quiz.dto.request.AnswerQuizSolvedRequest;
import soongsil.kidbean.server.quiz.dto.response.AnswerQuizResponse;
import soongsil.kidbean.server.quiz.dto.response.AnswerQuizSolveScoreResponse;

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
    public void solveAnswerQuiz() throws Exception {
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
                        .with(csrf())
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.results.score").value(returnScore));
    }
}