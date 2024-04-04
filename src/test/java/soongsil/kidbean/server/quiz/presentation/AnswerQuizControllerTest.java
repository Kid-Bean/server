package soongsil.kidbean.server.quiz.presentation;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER;
import static soongsil.kidbean.server.quiz.fixture.AnswerQuizFixture.ANSWER_QUIZ;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import soongsil.kidbean.server.quiz.application.AnswerQuizService;
import soongsil.kidbean.server.quiz.dto.response.AnswerQuizResponse;

@WebMvcTest(AnswerQuizController.class)
@MockBean(JpaMetamodelMappingContext.class)
class AnswerQuizControllerTest {

    @MockBean
    AnswerQuizService answerQuizService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("랜덤 AnswerQuiz 생성 요청")
    void getRandomAnswerQuiz() throws Exception {

        //given
        Long memberId = MEMBER.getMemberId();
        AnswerQuizResponse answerQuizResponse = AnswerQuizResponse.from(ANSWER_QUIZ);
        given(answerQuizService.selectRandomAnswerQuiz(memberId))
                .willReturn(answerQuizResponse);

        //when
        ResultActions resultActions = mockMvc.perform(get("/quiz/answer/{memberId}", memberId)
                        .param("memberId", memberId.toString()))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.results.question").value(ANSWER_QUIZ.getQuestion()));
    }
}