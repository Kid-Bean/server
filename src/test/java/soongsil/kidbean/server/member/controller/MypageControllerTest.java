package soongsil.kidbean.server.member.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import soongsil.kidbean.server.member.application.MypageService;
import soongsil.kidbean.server.member.dto.response.SolvedImageInfo;
import soongsil.kidbean.server.member.dto.response.SolvedImageListResponse;
import soongsil.kidbean.server.member.presentation.MypageController;

@WebMvcTest({MypageController.class})
@MockBean(JpaMetamodelMappingContext.class)
public class MypageControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MypageService mypageService;

    @Test
    public void findSolvedImageTest() throws Exception {
        //given
        List<SolvedImageInfo> list = new ArrayList<>();
        SolvedImageListResponse response = new SolvedImageListResponse(list);
        System.out.println(response);
        given(mypageService.findSolvedImage(any(Long.class)))
                .willReturn(response);
        System.out.println(response);

        ObjectMapper objectMapper = new ObjectMapper();

        Long memberId = 1L;
        //when
        ResultActions resultActions = mockMvc.perform(
                        MockMvcRequestBuilders.get("/mypage/solved/image/list/{memberId}", memberId)
                                .param("memberId", memberId.toString()))
                .andDo(MockMvcResultHandlers.print());
        //then
        resultActions.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.solvedList")
                        .value(objectMapper.writeValueAsString(response.solvedList())));
    }
}
