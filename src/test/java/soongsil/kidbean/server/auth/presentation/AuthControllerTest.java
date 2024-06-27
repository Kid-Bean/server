package soongsil.kidbean.server.auth.presentation;


import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import soongsil.kidbean.server.auth.application.AuthService;
import soongsil.kidbean.server.auth.dto.request.LoginRequest;
import soongsil.kidbean.server.auth.dto.request.ReissueRequest;
import soongsil.kidbean.server.auth.dto.response.LoginResponse;
import soongsil.kidbean.server.auth.dto.response.ReissueResponse;
import soongsil.kidbean.server.global.application.config.CommonControllerTest;
import soongsil.kidbean.server.member.repository.MemberRepository;


@WebMvcTest(AuthController.class)
class AuthControllerTest extends CommonControllerTest {

    @MockBean
    private AuthService authService;

    //테스트용 토큰 발급 때문에 필요한 의존성
    @MockBean
    private MemberRepository memberRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void socialLogin() throws Exception {
        //given
        LoginRequest loginRequest = new LoginRequest("access token");
        LoginResponse loginResponse = new LoginResponse("access token", "refresh token");
        String provider = "provider";

        given(authService.signIn(loginRequest, provider)).willReturn(loginResponse);

        //when
        ResultActions resultActions = mockMvc.perform(
                post("/auth/login/{provider}", provider).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest))).andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.results.accessToken").value(loginResponse.accessToken()))
                .andExpect(jsonPath("$.results.refreshToken").value(loginResponse.refreshToken()));
    }

    @Test
    void reissue() throws Exception {
        //given
        ReissueRequest reissueRequest = new ReissueRequest("refresh token");
        ReissueResponse reissueResponse = new ReissueResponse("access token");

        given(authService.reissueAccessToken(reissueRequest)).willReturn(reissueResponse);

        //when
        ResultActions resultActions = mockMvc.perform(
                        post("/auth/reissue")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(reissueRequest)))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.results.accessToken").value(reissueResponse.accessToken()));
    }
}