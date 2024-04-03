package soongsil.kidbean.server.mypage.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static soongsil.kidbean.server.quiz.fixture.ImageQuizFixture.IMAGE_QUIZ_ANIMAL;
import static soongsil.kidbean.server.quiz.fixture.ImageQuizSolvedFixture.IMAGE_QUIZ_SOLVED_ANIMAL_TRUE;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import soongsil.kidbean.server.mypage.application.QuizSolvedService;
import soongsil.kidbean.server.mypage.dto.response.SolvedImageInfo;
import soongsil.kidbean.server.mypage.dto.response.SolvedImageListResponse;
import soongsil.kidbean.server.mypage.presentation.QuizSolvedController;
import soongsil.kidbean.server.quiz.fixture.ImageQuizSolvedFixture;

@WebMvcTest({QuizSolvedController.class})
@MockBean(JpaMetamodelMappingContext.class)
public class QuizSolvedTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    QuizSolvedService quizSolvedService;

    @Test
    public void findSolvedImageTest() throws Exception {
        //given
        List<SolvedImageInfo> list = new ArrayList<>();
        list.add(SolvedImageInfo.from(IMAGE_QUIZ_SOLVED_ANIMAL_TRUE));
        System.out.println(SolvedImageInfo.from(IMAGE_QUIZ_SOLVED_ANIMAL_TRUE));
        list.add(SolvedImageInfo.from(ImageQuizSolvedFixture.IMAGE_QUIZ_SOLVED_ANIMAL_FALSE));
        SolvedImageListResponse response = new SolvedImageListResponse(list);
        System.out.println(response);
        given(quizSolvedService.findSolvedImage(any(Long.class)))
                .willReturn(response);
        System.out.println(response);

        ObjectMapper objectMapper = new ObjectMapper();

        Long memberId = 1L;
        //when
        ResultActions resultActions = mockMvc.perform(
                        MockMvcRequestBuilders.get("/mypage/solved/image/list/{memberId}", memberId)
                                .param("memberId", memberId.toString()))
                .andDo(MockMvcResultHandlers.print());
        //then
        resultActions.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.solvedList")
                        .value(objectMapper.writeValueAsString(response.solvedList())));
    }
}
