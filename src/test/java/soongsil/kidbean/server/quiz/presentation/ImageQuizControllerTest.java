package soongsil.kidbean.server.quiz.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static soongsil.kidbean.server.quiz.fixture.ImageQuizFixture.IMAGE_QUIZ_ANIMAL;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import soongsil.kidbean.server.quiz.application.ImageQuizService;
import soongsil.kidbean.server.quiz.dto.response.ImageQuizResponse;

@WebMvcTest(ImageQuizController.class)
@MockBean(JpaMetamodelMappingContext.class)
class ImageQuizControllerTest {

    @MockBean
    private ImageQuizService imageQuizService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void 랜덤_이미지_생성_요청() throws Exception {
        //given
        ImageQuizResponse imageQuizResponse = ImageQuizResponse.from(IMAGE_QUIZ_ANIMAL);
        Long memberId = 1L;
        given(imageQuizService.selectRandomProblem(any(Long.class)))
                .willReturn(imageQuizResponse);

        //when
        ResultActions resultActions = mockMvc.perform(get("/quiz/image/{memberId}", memberId)
                        .param("memberId", memberId.toString()))
                .andDo(print());

        //then
        //JSON 형태로 응답이 왔는지 확인
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.imageUrl").value(imageQuizResponse.imageUrl()))
                .andExpect(jsonPath("$.category").value(imageQuizResponse.category()))
                .andExpect(jsonPath("$.answer").value(imageQuizResponse.answer()))
                .andExpect(jsonPath("$.title").value(imageQuizResponse.title()));
    }
}