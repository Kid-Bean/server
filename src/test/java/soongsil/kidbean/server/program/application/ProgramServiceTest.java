package soongsil.kidbean.server.program.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER1;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;
import soongsil.kidbean.server.global.application.S3Uploader;
import soongsil.kidbean.server.global.domain.S3Info;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.program.domain.DepartmentInfo;
import soongsil.kidbean.server.program.domain.OpenDay;
import soongsil.kidbean.server.program.domain.Program;
import soongsil.kidbean.server.program.domain.ProgramInfo;
import soongsil.kidbean.server.program.domain.type.Date;
import soongsil.kidbean.server.program.domain.type.ProgramCategory;
import soongsil.kidbean.server.program.dto.response.ProgramDetailResponse;
import soongsil.kidbean.server.program.dto.response.ProgramResponse;
import soongsil.kidbean.server.program.dto.response.ProgramResponseList;
import soongsil.kidbean.server.program.exception.ProgramNotFoundException;
import soongsil.kidbean.server.program.repository.DayRepository;
import soongsil.kidbean.server.program.repository.ProgramRepository;

@ExtendWith(MockitoExtension.class)
class ProgramServiceTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private ProgramRepository programRepository;
    @Mock
    private S3Uploader s3Uploader;
    @Mock
    private DayRepository dayRepository;
    @Mock
    private StarService starService;

    @InjectMocks
    private ProgramService programService;

    private Program program(long id, ProgramCategory category) {
        Program program = Program.builder()
                .programInfo(ProgramInfo.builder()
                        .programTitle("title" + id)
                        .contentTitle("contentTitle" + id)
                        .place("place" + id)
                        .content("content" + id)
                        .programCategory(category)
                        .programS3Info(new S3Info("programUrl" + id, null, null))
                        .build())
                .departmentInfo(DepartmentInfo.builder()
                        .departmentName("department" + id)
                        .phoneNumber("010-0000-000" + id)
                        .departmentS3Info(new S3Info("departmentUrl" + id, null, null))
                        .build())
                .member(MEMBER1)
                .build();
        ReflectionTestUtils.setField(program, "programId", id);
        return program;
    }

    @Test
    @DisplayName("프로그램 목록 조회 시 즐겨찾기 ID는 단 한 번만 조회한다 (N+1 방지)")
    void getProgramInfoList_findStarredProgramIdsCalledOnce() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        List<ProgramCategory> categories = List.of(ProgramCategory.HOSPITAL);

        Program program1 = program(1L, ProgramCategory.HOSPITAL);
        Program program2 = program(2L, ProgramCategory.HOSPITAL);
        Program program3 = program(3L, ProgramCategory.HOSPITAL);
        Page<Program> programPage = new PageImpl<>(List.of(program1, program2, program3), pageable, 3);

        given(programRepository.findAllByProgramInfo_ProgramCategoryIn(categories, pageable))
                .willReturn(programPage);
        given(starService.findStarredProgramIds(eq(MEMBER1.getMemberId()), anyList()))
                .willReturn(Set.of(1L, 3L));

        // when
        ProgramResponseList result = programService.getProgramInfoList(MEMBER1.getMemberId(), categories, pageable);

        // then: 프로그램마다 호출하지 않고 정확히 한 번만 조회한다
        verify(starService, times(1)).findStarredProgramIds(eq(MEMBER1.getMemberId()), anyList());

        List<ProgramResponse> responses = result.programResponseList();
        assertThat(responses).hasSize(3);
        assertThat(responses).extracting(ProgramResponse::programId)
                .containsExactly(1L, 2L, 3L);
        assertThat(responses).extracting(ProgramResponse::isStar)
                .containsExactly(true, false, true);
    }

    @Test
    @DisplayName("카테고리에 해당하는 프로그램이 없으면 예외를 던진다")
    void getProgramInfoList_throwWhenEmpty() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        List<ProgramCategory> categories = List.of(ProgramCategory.HOSPITAL);
        Page<Program> emptyPage = new PageImpl<>(List.of(), pageable, 0);

        given(programRepository.findAllByProgramInfo_ProgramCategoryIn(categories, pageable))
                .willReturn(emptyPage);

        // when & then
        assertThatThrownBy(() -> programService.getProgramInfoList(MEMBER1.getMemberId(), categories, pageable))
                .isInstanceOf(ProgramNotFoundException.class);
    }

    @Test
    @DisplayName("프로그램 상세 조회 시 운영 요일과 함께 상세 정보를 반환한다")
    void getProgramInfo_found() {
        // given
        Program program = program(1L, ProgramCategory.HOSPITAL);
        OpenDay openDay = OpenDay.builder().date(Date.MONDAY).program(program).build();

        given(programRepository.findById(1L)).willReturn(Optional.of(program));
        given(dayRepository.findAllByProgram(program)).willReturn(List.of(openDay));

        // when
        ProgramDetailResponse response = programService.getProgramInfo(1L);

        // then
        assertThat(response.programId()).isEqualTo(1L);
        assertThat(response.programTitle()).isEqualTo("title1");
        assertThat(response.date()).containsExactly("월");
    }

    @Test
    @DisplayName("존재하지 않는 프로그램을 상세 조회하면 예외를 던진다")
    void getProgramInfo_notFound() {
        // given
        given(programRepository.findById(any())).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> programService.getProgramInfo(999L))
                .isInstanceOf(ProgramNotFoundException.class);
    }
}
