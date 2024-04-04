package soongsil.kidbean.server.quiz.presentation;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER;
import static soongsil.kidbean.server.quiz.fixture.SentenceQuizFixture.SENTENCE_QUIZ;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import soongsil.kidbean.server.quiz.application.SentenceQuizService;
import soongsil.kidbean.server.quiz.domain.SentenceQuizWord;
import soongsil.kidbean.server.quiz.dto.response.SentenceQuizResponse;

@WebMvcTest(SentenceQuizController.class)
@MockBean(JpaMetamodelMappingContext.class)
class SentenceQuizControllerTest {

    @MockBean
    private SentenceQuizService sentenceQuizService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("랜덤 SentenceQuiz 생성 요청")
    void getRandomSentenceQuiz() throws Exception {

        //given
        Long memberId = MEMBER.getMemberId();
        SentenceQuizResponse sentenceQuizResponse = SentenceQuizResponse.from(SENTENCE_QUIZ);
        List<SentenceQuizWord> wordList = SENTENCE_QUIZ.getWords();

        given(sentenceQuizService.selectRandomSentenceQuiz(memberId))
                .willReturn(sentenceQuizResponse);

        //when
        ResultActions resultActions = mockMvc.perform(get("/quiz/sentence/{memberId}", memberId)
                        .param("memberId", memberId.toString()))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.results.title").value("sentenceQuiz"))
                .andExpect(jsonPath("$.results.quizId").value(SENTENCE_QUIZ.getQuizId()))
                .andExpect(jsonPath("$.results.words[0].content").value(wordList.get(0).getContent()))
                .andExpect(jsonPath("$.results.words[1].content").value(wordList.get(1).getContent()))
                .andExpect(jsonPath("$.results.words[2].content").value(wordList.get(2).getContent()));
    }
}