package soongsil.kidbean.server.member.presentation;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import soongsil.kidbean.server.member.dto.response.ImageQuizScoreResponse;
import soongsil.kidbean.server.member.dto.response.SolvedAnswerQuizListResponse;
import soongsil.kidbean.server.member.dto.response.SolvedImageDetailResponse;
import soongsil.kidbean.server.member.dto.response.SolvedImageListResponse;
import soongsil.kidbean.server.member.dto.response.SolvedWordDetailResponse;
import soongsil.kidbean.server.member.dto.response.SolvedWordQuizListResponse;
import soongsil.kidbean.server.member.dto.response.UseWordList;
import soongsil.kidbean.server.summary.application.SummaryService;

@WebMvcTest(MypageController.class)
class MypageControllerTest extends CommonControllerTest {

    @MockBean
    QuizSolvedResultService quizSolvedResultService;

    @MockBean
    SummaryService summaryService;

    @MockBean
    VoiceSolvedResultService voiceSolvedResultService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("푼 이미지 퀴즈 목록 조회")
    void findSolvedImageList() throws Exception {
        // given
        given(quizSolvedResultService.findSolvedImage(eq(1L), anyBoolean()))
                .willReturn(SolvedImageListResponse.from(List.of()));

        // when
        ResultActions resultActions = mockMvc.perform(get("/mypage/solved/image/list")
                        .param("isCorrect", "true"))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("푼 이미지 퀴즈 상세 조회")
    void findSolvedImageDetail() throws Exception {
        // given
        Long solvedId = 1L;
        SolvedImageDetailResponse response = new SolvedImageDetailResponse(
                solvedId, "https://www.example.com/image", "answer", "kidAnswer");
        given(quizSolvedResultService.solvedImageDetail(solvedId)).willReturn(response);

        // when
        ResultActions resultActions = mockMvc.perform(get("/mypage/solved/image/{solvedId}", solvedId))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("푼 단어 퀴즈 목록 조회")
    void findSolvedWordList() throws Exception {
        // given
        given(quizSolvedResultService.findSolvedWord(eq(1L)))
                .willReturn(SolvedWordQuizListResponse.from(List.of()));

        // when
        ResultActions resultActions = mockMvc.perform(get("/mypage/solved/word/list"))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("푼 단어 퀴즈 상세 조회")
    void findSolvedWordDetail() throws Exception {
        // given
        Long solvedId = 1L;
        SolvedWordDetailResponse response = new SolvedWordDetailResponse(
                solvedId, "title", List.of("word"), "answer", "kidAnswer");
        given(quizSolvedResultService.solvedWordDetail(solvedId)).willReturn(response);

        // when
        ResultActions resultActions = mockMvc.perform(get("/mypage/solved/word/{solvedId}", solvedId))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("푼 음성 퀴즈 목록 조회")
    void findSolvedVoiceList() throws Exception {
        // given
        given(voiceSolvedResultService.findSolvedAnswerQuiz(eq(1L)))
                .willReturn(SolvedAnswerQuizListResponse.from(List.of()));

        // when
        ResultActions resultActions = mockMvc.perform(get("/mypage/solved/voice/list"))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("자주 사용한 단어 목록 조회")
    void findAllUseWordList() throws Exception {
        // given
        given(voiceSolvedResultService.findTop5UseWordList(eq(1L)))
                .willReturn(UseWordList.from(List.of()));

        // when
        ResultActions resultActions = mockMvc.perform(get("/mypage/solved/voice/use-word/list"))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("이미지 퀴즈 점수 통계 조회")
    void findImageQuizScoreInfo() throws Exception {
        // given
        given(summaryService.findImageQuizScore(eq(1L)))
                .willReturn(ImageQuizScoreResponse.of(List.of(), List.of()));

        // when
        ResultActions resultActions = mockMvc.perform(get("/mypage/solved/image/result"))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk());
    }
}
