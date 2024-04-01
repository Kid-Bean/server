package soongsil.kidbean.server.quiz.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER;
import static soongsil.kidbean.server.quiz.fixture.ImageQuizFixture.IMAGE_QUIZ_ANIMAL;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import soongsil.kidbean.server.quiz.application.ImageQuizService;
import soongsil.kidbean.server.quiz.domain.type.Level;
import soongsil.kidbean.server.quiz.dto.request.ImageQuizSolvedRequest;
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
        Long memberId = MEMBER.getMemberId();
        given(imageQuizService.selectRandomImageQuiz(any(Long.class)))
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

    @Test
    void 문제_풀기_요청() throws Exception {
        //given
        List<ImageQuizSolvedRequest> request = Collections.singletonList(
                new ImageQuizSolvedRequest(IMAGE_QUIZ_ANIMAL.getQuizId(), IMAGE_QUIZ_ANIMAL.getAnswer())
        );
        Long memberId = MEMBER.getMemberId();

        given(imageQuizService.solveImageQuizzes(anyList(), any(Long.class)))
                .willReturn(Level.getPoint(IMAGE_QUIZ_ANIMAL.getLevel()));

        //when
        ResultActions resultActions = mockMvc.perform(post("/quiz/image/{memberId}", memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(Level.getPoint(IMAGE_QUIZ_ANIMAL.getLevel()))));
    }
}