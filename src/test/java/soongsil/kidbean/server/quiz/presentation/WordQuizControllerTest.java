package soongsil.kidbean.server.quiz.presentation;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER1;
import static soongsil.kidbean.server.quiz.fixture.WordQuizFixture.WORD_QUIZ;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import soongsil.kidbean.server.global.application.config.CommonControllerTest;
import soongsil.kidbean.server.quiz.application.WordQuizService;
import soongsil.kidbean.server.quiz.domain.Word;
import soongsil.kidbean.server.quiz.dto.response.WordQuizSolveListResponse;
import soongsil.kidbean.server.quiz.dto.response.WordQuizSolveResponse;

@WebMvcTest(WordQuizController.class)
class WordQuizControllerTest extends CommonControllerTest {

    @MockBean
    private WordQuizService wordQuizService;

    @Autowired
    private MockMvc mockMvc;

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
}