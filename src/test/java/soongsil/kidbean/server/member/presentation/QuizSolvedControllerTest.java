package soongsil.kidbean.server.member.presentation;


import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER1;
import static soongsil.kidbean.server.quiz.fixture.QuizSolvedFixture.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import soongsil.kidbean.server.global.application.config.CommonControllerTest;
import soongsil.kidbean.server.member.application.QuizSolvedResultService;
import soongsil.kidbean.server.member.application.VoiceSolvedResultService;
import soongsil.kidbean.server.member.dto.response.SolvedImageInfo;
import soongsil.kidbean.server.member.dto.response.SolvedImageListResponse;
import soongsil.kidbean.server.summary.application.SummaryService;

@WebMvcTest(MypageController.class)
class QuizSolvedControllerTest extends CommonControllerTest {

    @MockBean
    private QuizSolvedResultService quizSolvedResultService;

    @MockBean
    private SummaryService summaryService;

    @MockBean
    private VoiceSolvedResultService voiceSolvedResultService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("이미지 퀴즈 리스트 반환 Controller Test")
    void findSolvedImageTest() throws Exception {
        //given
        SolvedImageListResponse solvedImageListResponse =
                SolvedImageListResponse.from(List.of(SolvedImageInfo.from(IMAGE_QUIZ_SOLVED_ANIMAL_TRUE)));
        Boolean isCorrect = true;

        given(quizSolvedResultService.findSolvedImage(MEMBER1.getMemberId(), isCorrect))
                .willReturn(solvedImageListResponse);

        //when
        ResultActions resultActions = mockMvc.perform(get("/mypage/solved/image/list")
                        .param("isCorrect", "true"))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.results.solvedList[0].solvedId")
                        .value(IMAGE_QUIZ_SOLVED_ANIMAL_TRUE.getSolvedId()));
    }

}