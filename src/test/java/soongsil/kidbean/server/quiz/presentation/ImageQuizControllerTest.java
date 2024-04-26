package soongsil.kidbean.server.quiz.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static soongsil.kidbean.server.quiz.fixture.ImageQuizFixture.IMAGE_QUIZ_ANIMAL;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import soongsil.kidbean.server.global.application.config.CommonControllerTest;
import soongsil.kidbean.server.quiz.application.ImageQuizService;
import soongsil.kidbean.server.quiz.domain.type.Level;
import soongsil.kidbean.server.quiz.dto.request.QuizSolvedListRequest;
import soongsil.kidbean.server.quiz.dto.request.QuizSolvedRequest;
import soongsil.kidbean.server.quiz.dto.response.ImageQuizResponse;
import soongsil.kidbean.server.quiz.dto.response.ImageQuizSolveScoreResponse;

@WebMvcTest(ImageQuizController.class)
class ImageQuizControllerTest extends CommonControllerTest {

    @MockBean
    private ImageQuizService imageQuizService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("랜덤 이미지 생성 요청")
    void getRandomImageQuiz() throws Exception {
        //given
        ImageQuizResponse imageQuizResponse = ImageQuizResponse.from(IMAGE_QUIZ_ANIMAL);
        given(imageQuizService.selectRandomImageQuiz(any(Long.class)))
                .willReturn(imageQuizResponse);

        //when
        ResultActions resultActions = mockMvc.perform(get("/quiz/image/solve"))
                .andDo(print());

        //then
        //JSON 형태로 응답이 왔는지 확인
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.results.s3Url").value(imageQuizResponse.s3Url()))
                .andExpect(jsonPath("$.results.answer").value(imageQuizResponse.answer()));
    }

    @Test
    @DisplayName("문제 풀기 요청")
    void solveImageQuizzes() throws Exception {
        //given
        QuizSolvedListRequest request = new QuizSolvedListRequest(Collections.singletonList(
                new QuizSolvedRequest(IMAGE_QUIZ_ANIMAL.getQuizId(), IMAGE_QUIZ_ANIMAL.getAnswer())
        ));

        given(imageQuizService.solveImageQuizzes(anyList(), any(Long.class)))
                .willReturn(ImageQuizSolveScoreResponse.scoreFrom(
                        Level.getPoint(IMAGE_QUIZ_ANIMAL.getLevel())));

        //when
        ResultActions resultActions = mockMvc.perform(post("/quiz/image/solve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(csrf()))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.results.score")
                        .value(String.valueOf(Level.getPoint(IMAGE_QUIZ_ANIMAL.getLevel()))));
    }
}