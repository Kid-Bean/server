package soongsil.kidbean.server.quiz.presentation;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER;
import static soongsil.kidbean.server.quiz.fixture.WordQuizFixture.WORD_QUIZ;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import soongsil.kidbean.server.quiz.application.WordQuizService;
import soongsil.kidbean.server.quiz.domain.WordQuizWord;
import soongsil.kidbean.server.quiz.dto.response.WordQuizResponse;

@WebMvcTest(WordQuizController.class)
@MockBean(JpaMetamodelMappingContext.class)
class WordQuizControllerTest {

    @MockBean
    private WordQuizService wordQuizService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("랜덤 WordQuiz 생성 요청")
    void getRandomWordQuiz() throws Exception {

        //given
        Long memberId = MEMBER.getMemberId();
        WordQuizResponse wordQuizResponse = WordQuizResponse.from(WORD_QUIZ);
        List<WordQuizWord> wordList = WORD_QUIZ.getWords();

        given(wordQuizService.selectRandomWordQuiz(memberId))
                .willReturn(wordQuizResponse);

        //when
        ResultActions resultActions = mockMvc.perform(get("/quiz/word/{memberId}", memberId)
                        .param("memberId", memberId.toString()))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.results.title").value("WordQuiz"))
                .andExpect(jsonPath("$.results.quizId").value(WORD_QUIZ.getQuizId()))
                .andExpect(jsonPath("$.results.words[0].content").value(wordList.get(0).getContent()))
                .andExpect(jsonPath("$.results.words[1].content").value(wordList.get(1).getContent()))
                .andExpect(jsonPath("$.results.words[2].content").value(wordList.get(2).getContent()));
    }
}