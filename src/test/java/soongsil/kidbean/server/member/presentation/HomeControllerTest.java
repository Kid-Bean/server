package soongsil.kidbean.server.member.presentation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import soongsil.kidbean.server.global.application.config.CommonControllerTest;
import soongsil.kidbean.server.member.application.HomeService;
import soongsil.kidbean.server.member.dto.response.HomeResponse;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER1;

@WebMvcTest(HomeController.class)
class HomeControllerTest extends CommonControllerTest {

    @MockBean
    HomeService homeService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("홈 정보 가져오기")
    void getHomeInfo() throws Exception {
        // given
        String s3Url = "https://www.example.com";
        HomeResponse response = new HomeResponse(MEMBER1.getName(), LocalDateTime.now(), s3Url, MEMBER1.getScore());

        given(homeService.getHomeInfo(anyLong())).willReturn(response);

        // when
        ResultActions resultActions = mockMvc.perform(get("/home"))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.results.name").value(MEMBER1.getName()));
    }
}
