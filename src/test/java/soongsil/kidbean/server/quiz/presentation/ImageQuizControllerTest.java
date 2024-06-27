package soongsil.kidbean.server.quiz.presentation;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static soongsil.kidbean.server.quiz.fixture.ImageQuizFixture.IMAGE_QUIZ_ANIMAL1;
import static soongsil.kidbean.server.quiz.fixture.ImageQuizFixture.IMAGE_QUIZ_NONE;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import soongsil.kidbean.server.global.application.config.CommonControllerTest;
import soongsil.kidbean.server.quiz.application.ImageQuizService;
import soongsil.kidbean.server.quiz.domain.type.Level;
import soongsil.kidbean.server.quiz.dto.request.ImageQuizUpdateRequest;
import soongsil.kidbean.server.quiz.dto.request.ImageQuizUploadRequest;
import soongsil.kidbean.server.quiz.dto.request.QuizSolvedListRequest;
import soongsil.kidbean.server.quiz.dto.request.QuizSolvedRequest;
import soongsil.kidbean.server.quiz.dto.response.*;

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
        ImageQuizSolveListResponse imageQuizSolveListResponse =
                new ImageQuizSolveListResponse(List.of(ImageQuizSolveResponse.from(IMAGE_QUIZ_ANIMAL1)));

        given(imageQuizService.selectRandomImageQuizList(anyLong(), anyInt())).willReturn(imageQuizSolveListResponse);

        //when
        ResultActions resultActions = mockMvc.perform(get("/quiz/image/solve"))
                .andDo(print());

        //then
        //JSON 형태로 응답이 왔는지 확인
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.results.imageQuizSolveResponseList[0].s3Url")
                        .value(imageQuizSolveListResponse.imageQuizSolveResponseList().get(0).s3Url()))
                .andExpect(jsonPath("$.results.imageQuizSolveResponseList[0].answer")
                        .value(imageQuizSolveListResponse.imageQuizSolveResponseList().get(0).answer()));
    }

    @Test
    @DisplayName("문제 풀기 요청")
    void solveImageQuizzes() throws Exception {
        //given
        QuizSolvedListRequest request = new QuizSolvedListRequest(Collections.singletonList(
                new QuizSolvedRequest(IMAGE_QUIZ_ANIMAL1.getQuizId(), IMAGE_QUIZ_ANIMAL1.getAnswer())
        ));

        given(imageQuizService.solveImageQuizzes(anyList(), anyLong()))
                .willReturn(ImageQuizSolveScoreResponse.scoreFrom(Level.getPoint(IMAGE_QUIZ_ANIMAL1.getLevel())));

        //when
        ResultActions resultActions = mockMvc.perform(post("/quiz/image/solve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print());
        //TODO 위와 같이 csrf 가 적용된 부분들 나중에 spring rest docs 사용 시 @AutoConfigureRestDocs로 공통 처리해주기
        //CommonControllerTest 에서 MockMvc 에 설정 하면 됨.

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.results.score")
                        .value(String.valueOf(Level.getPoint(IMAGE_QUIZ_ANIMAL1.getLevel()))));
    }

    @Test
    @DisplayName("추가한 ImageQuiz 상세 정보 가져오기")
    void getImageQuizById() throws Exception {
        // given
        ImageQuizMemberDetailResponse response = ImageQuizMemberDetailResponse.from(IMAGE_QUIZ_NONE);

        given(imageQuizService.getImageQuizById(anyLong(), anyLong())).willReturn(response);

        // when
        ResultActions resultActions = mockMvc.perform(get("/quiz/image/member/1"))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.results.answer")
                        .value(String.valueOf(IMAGE_QUIZ_NONE.getAnswer())));
    }

    @Test
    @DisplayName("추가한 ImageQuiz 리스트 가져오기")
    void getAllImageQuizByMember() throws Exception {
        // given
        List<ImageQuizMemberResponse> responses = List.of(ImageQuizMemberResponse.from(IMAGE_QUIZ_NONE));

        given(imageQuizService.getAllImageQuizByMember(anyLong())).willReturn(responses);

        // when
        ResultActions resultActions = mockMvc.perform(get("/quiz/image/member"))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.results[0].title")
                        .value(String.valueOf(IMAGE_QUIZ_NONE.getTitle())));
    }

    @Test
    @DisplayName("ImageQuiz 등록하기")
    void uploadImageQuiz() throws Exception {
        // given
        ImageQuizUploadRequest request = new ImageQuizUploadRequest(IMAGE_QUIZ_NONE.getTitle(),
                IMAGE_QUIZ_NONE.getAnswer(), IMAGE_QUIZ_NONE.getQuizCategory());

        MockMultipartFile requestPartFile = new MockMultipartFile("imageQuizUploadRequest", "", "application/json",
                objectMapper.writeValueAsString(request).getBytes());
        MockMultipartFile multipartFile = new MockMultipartFile("multipartFile", "test.jpg", "image/jpeg",
                "test image content".getBytes());

        // when
        ResultActions resultActions = mockMvc.perform(multipart("/quiz/image/member")
                        .file(requestPartFile)
                        .file(multipartFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("ImageQuiz 수정하기")
    void updateImageQuiz() throws Exception {
        // given
        ImageQuizUpdateRequest updateRequest = new ImageQuizUpdateRequest(IMAGE_QUIZ_NONE.getTitle(),
                IMAGE_QUIZ_NONE.getAnswer(), IMAGE_QUIZ_NONE.getQuizCategory());

        MockMultipartFile requestPartFile = new MockMultipartFile("imageQuizUpdateRequest", "", "application/json",
                objectMapper.writeValueAsString(updateRequest).getBytes());
        MockMultipartFile multipartFile = new MockMultipartFile("multipartFile", "test.jpg", "image/jpeg",
                "test image content".getBytes());

        // when
        ResultActions resultActions = mockMvc.perform(
                        MockMvcRequestBuilders.multipart("/quiz/image/member/" + IMAGE_QUIZ_NONE.getQuizId())
                                .file(requestPartFile) // JSON 데이터를 파일 파트로 추가
                                .file(multipartFile) // 이미지 파일 추가
                                .with(request -> {
                                    request.setMethod("PUT");
                                    return request;
                                }) // 요청 메소드를 PUT으로 설정
                                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("ImageQuiz 삭제하기")
    void deleteImageQuiz() throws Exception {
        // given

        // when
        ResultActions resultActions = mockMvc.perform(delete("/quiz/image/member/" + IMAGE_QUIZ_NONE.getQuizId()))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk());
    }
}