package soongsil.kidbean.server.program.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import com.fasterxml.jackson.databind.ObjectMapper;
import soongsil.kidbean.server.global.application.config.CommonControllerTest;
import soongsil.kidbean.server.program.application.ProgramService;
import soongsil.kidbean.server.program.domain.type.ProgramCategory;
import soongsil.kidbean.server.program.dto.request.EnrollProgramRequest;
import soongsil.kidbean.server.program.dto.request.UpdateProgramRequest;
import soongsil.kidbean.server.program.dto.response.ProgramDetailResponse;
import soongsil.kidbean.server.program.dto.response.ProgramResponseList;

@WebMvcTest(ProgramController.class)
class ProgramControllerTest extends CommonControllerTest {

    @MockBean
    ProgramService programService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("프로그램 목록 조회")
    void getProgramListInfo() throws Exception {
        // given
        ProgramResponseList response = ProgramResponseList.from(List.of());
        given(programService.getProgramInfoList(eq(1L), anyList(), any(Pageable.class)))
                .willReturn(response);

        // when
        ResultActions resultActions = mockMvc.perform(get("/programs")
                        .param("programCategoryList", "HOSPITAL")
                        .param("page", "0"))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("좋아요한 프로그램 목록 조회")
    void getStarProgramListInfo() throws Exception {
        // given
        ProgramResponseList response = ProgramResponseList.from(List.of());
        given(programService.getStarProgramInfoList(eq(1L), any(Pageable.class)))
                .willReturn(response);

        // when
        ResultActions resultActions = mockMvc.perform(get("/programs/star")
                        .param("page", "0"))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("프로그램 상세 조회")
    void getProgramInfo() throws Exception {
        // given
        Long programId = 1L;
        ProgramDetailResponse response = ProgramDetailResponse.builder()
                .programId(programId)
                .programTitle("title")
                .contentTitle("contentTitle")
                .content("content")
                .departmentName("department")
                .place("place")
                .phoneNumber("010-0000-0000")
                .programS3Url("https://www.example.com/program")
                .departmentS3Url("https://www.example.com/department")
                .date(List.of("MONDAY"))
                .build();
        given(programService.getProgramInfo(programId)).willReturn(response);

        // when
        ResultActions resultActions = mockMvc.perform(get("/programs/{programId}", programId))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("프로그램 삭제")
    void deleteProgram() throws Exception {
        // given
        Long programId = 1L;
        willDoNothing().given(programService).deleteProgram(programId);

        // when
        ResultActions resultActions = mockMvc.perform(delete("/programs/{programId}", programId))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk());
        then(programService).should(times(1)).deleteProgram(programId);
    }

    @Test
    @DisplayName("프로그램 등록")
    void createProgram() throws Exception {
        // given
        EnrollProgramRequest request = new EnrollProgramRequest(
                "title", "contentTitle", "content", ProgramCategory.HOSPITAL,
                List.of("MONDAY"), "place", "department", "010-0000-0000");

        MockMultipartFile jsonPart = new MockMultipartFile(
                "enrollProgramRequest", "", MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsBytes(request));
        MockMultipartFile programImage = new MockMultipartFile(
                "programImage", "program.png", MediaType.IMAGE_PNG_VALUE, "program".getBytes());
        MockMultipartFile departmentImage = new MockMultipartFile(
                "departmentImage", "department.png", MediaType.IMAGE_PNG_VALUE, "department".getBytes());

        willDoNothing().given(programService)
                .createProgram(eq(1L), any(EnrollProgramRequest.class), any(), any());

        // when
        ResultActions resultActions = mockMvc.perform(multipart("/programs")
                        .file(jsonPart)
                        .file(programImage)
                        .file(departmentImage))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk());
        then(programService).should(times(1))
                .createProgram(eq(1L), any(EnrollProgramRequest.class), any(), any());
    }

    @Test
    @DisplayName("프로그램 수정")
    void editProgramInfo() throws Exception {
        // given
        UpdateProgramRequest request = new UpdateProgramRequest(
                1L, "title", "contentTitle", List.of("MONDAY"),
                "department", "place", "010-0000-0000", "content");

        MockMultipartFile jsonPart = new MockMultipartFile(
                "updateProgramRequest", "", MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsBytes(request));
        MockMultipartFile programImage = new MockMultipartFile(
                "programImage", "program.png", MediaType.IMAGE_PNG_VALUE, "program".getBytes());
        MockMultipartFile departmentImage = new MockMultipartFile(
                "departmentImage", "department.png", MediaType.IMAGE_PNG_VALUE, "department".getBytes());

        willDoNothing().given(programService)
                .editProgramInfo(any(UpdateProgramRequest.class), any(), any());

        // when
        ResultActions resultActions = mockMvc.perform(multipart("/programs")
                        .file(jsonPart)
                        .file(programImage)
                        .file(departmentImage)
                        .with(req -> {
                            req.setMethod("PUT");
                            return req;
                        }))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk());
        then(programService).should(times(1))
                .editProgramInfo(any(UpdateProgramRequest.class), any(), any());
    }
}
