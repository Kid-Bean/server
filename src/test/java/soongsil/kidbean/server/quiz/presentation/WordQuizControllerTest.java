package soongsil.kidbean.server.quiz.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import soongsil.kidbean.server.global.application.config.CommonControllerTest;
import soongsil.kidbean.server.quiz.application.WordQuizService;
import soongsil.kidbean.server.quiz.domain.Word;
import soongsil.kidbean.server.quiz.dto.request.WordQuizUpdateRequest;
import soongsil.kidbean.server.quiz.dto.request.WordQuizUploadRequest;
import soongsil.kidbean.server.quiz.dto.request.WordRequest;
import soongsil.kidbean.server.quiz.dto.response.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER1;
import static soongsil.kidbean.server.quiz.fixture.WordQuizFixture.WORD_QUIZ;

@WebMvcTest(WordQuizController.class)
class WordQuizControllerTest extends CommonControllerTest {

    @MockBean
    private WordQuizService wordQuizService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("랜덤 WordQuiz 생성 요청")
    void getRandomWordQuiz() throws Exception {

        //given
        WordQuizSolveListResponse wordQuizSolveListResponse = new WordQuizSolveListResponse(
                List.of(WordQuizSolveResponse.from(WORD_QUIZ)));
        List<Word> wordList = WORD_QUIZ.getWords();

        given(wordQuizService.selectRandomWordQuiz(MEMBER1.getMemberId(), 3))
                .willReturn(wordQuizSolveListResponse);

        //when
        ResultActions resultActions = mockMvc.perform(get("/quiz/word/solve"))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.results.wordQuizSolveResponseList[0].title").value(WORD_QUIZ.getTitle()))
                .andExpect(jsonPath("$.results.wordQuizSolveResponseList[0].quizId").value(WORD_QUIZ.getQuizId()))
                .andExpect(jsonPath("$.results.wordQuizSolveResponseList[0].words[0].content")
                        .value(wordList.get(0).getContent()))
                .andExpect(jsonPath("$.results.wordQuizSolveResponseList[0].words[1].content")
                        .value(wordList.get(1).getContent()))
                .andExpect(jsonPath("$.results.wordQuizSolveResponseList[0].words[2].content")
                        .value(wordList.get(2).getContent()));
    }

    @Test
    @DisplayName("WordQuiz 상세 정보 가져오기")
    void getWordQuizById() throws Exception {
        // given
        WordResponse wordList = WordResponse.from(WORD_QUIZ.getWords().get(0));
        WordQuizMemberDetailResponse response = new WordQuizMemberDetailResponse("Title", "content", List.of(wordList));

        given(wordQuizService.getWordQuizById(anyLong(), anyLong())).willReturn(response);

        // when
        ResultActions resultActions = mockMvc.perform(get("/quiz/word/member/1"))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.results.title").value(response.title()));
    }

    @Test
    @DisplayName("WordQuiz 리스트 가져오기")
    void getAllWordQuizByMember() throws Exception {
        // given
        WordQuizMemberResponse response = WordQuizMemberResponse.from(WORD_QUIZ);

        given(wordQuizService.getAllWordQuizByMember(anyLong())).willReturn(List.of(response));

        // when
        ResultActions resultActions = mockMvc.perform(get("/quiz/word/member"))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.results[0].title").value(response.title()));
    }

    @Test
    @DisplayName("WordQuiz 등록하기")
    void uploadWordQuiz() throws Exception {
        // given
        WordRequest wordRequest = new WordRequest("content");

        List<WordRequest> requests = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            requests.add(wordRequest);
        }

        WordQuizUploadRequest request = new WordQuizUploadRequest(anyString(), anyString(), requests);

        // when
        ResultActions resultActions = mockMvc.perform(post("/quiz/word/member")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("WordQuiz 수정하기")
    void updateWordQuiz() throws Exception {
        // given
        WordRequest wordRequest = new WordRequest("content");

        List<WordRequest> requests = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            requests.add(wordRequest);
        }

        WordQuizUpdateRequest request = new WordQuizUpdateRequest(anyString(), anyString(), requests);

        // when
        ResultActions resultActions = mockMvc.perform(put("/quiz/word/member/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("WordQuiz 삭제하기")
    void deleteWordQuiz() throws Exception {
        // given

        // when
        ResultActions resultActions = mockMvc.perform(delete("/quiz/word/member/1")
                        .with(csrf()))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk());
    }
}