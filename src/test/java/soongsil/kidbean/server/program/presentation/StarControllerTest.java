package soongsil.kidbean.server.program.presentation;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import soongsil.kidbean.server.global.application.config.CommonControllerTest;
import soongsil.kidbean.server.program.application.StarService;
import soongsil.kidbean.server.program.dto.response.StarResponse;

@WebMvcTest(StarController.class)
class StarControllerTest extends CommonControllerTest {

    @MockBean
    StarService starService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("즐겨찾기 추가/삭제")
    void saveStar() throws Exception {
        // given
        Long programId = 5L;
        given(starService.saveStar(eq(1L), anyLong())).willReturn(StarResponse.from("save"));

        // when
        ResultActions resultActions = mockMvc.perform(post("/program/star/{programId}", programId))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.results.starStatus").value("save"));
    }
}
