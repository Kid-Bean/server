package soongsil.kidbean.server.mypage.controller;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import soongsil.kidbean.server.mypage.application.QuizSolvedService;
import soongsil.kidbean.server.mypage.dto.response.SolvedImageInfo;
import soongsil.kidbean.server.mypage.dto.response.SolvedImageListResponse;
import soongsil.kidbean.server.mypage.presentation.QuizSolvedController;

@WebMvcTest({QuizSolvedController.class})
public class QuizSolvedTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    QuizSolvedService quizSolvedService;

    @Test
    public void findSolvedImageTest() throws Exception {
        //given
        List<SolvedImageInfo> list = new ArrayList<>();
        SolvedImageListResponse response = new SolvedImageListResponse(list);
        when(quizSolvedService.findSolvedImage(1L)).thenReturn(response);

        Long memberId = 1L;
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/mypage/solved/image/{memberId}", memberId)
                        .param("memberId", memberId.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        //then
        verify(quizSolvedService, times(1)).findSolvedImage(1L);
    }
}
