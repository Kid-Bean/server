package soongsil.kidbean.server.program.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
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
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.program.domain.Program;
import soongsil.kidbean.server.program.domain.Star;
import soongsil.kidbean.server.program.dto.response.StarResponse;
import soongsil.kidbean.server.program.repository.ProgramRepository;
import soongsil.kidbean.server.program.repository.StarRepository;

@ExtendWith(MockitoExtension.class)
class StarServiceTest {

    @Mock
    private StarRepository starRepository;
    @Mock
    private ProgramRepository programRepository;
    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private StarService starService;

    @Test
    @DisplayName("즐겨찾기가 없으면 새로 저장한다")
    void saveStar_save() {
        // given
        Program program = mock(Program.class);
        given(memberRepository.getByIdOrThrow(anyLong())).willReturn(MEMBER1);
        given(programRepository.findById(anyLong())).willReturn(Optional.of(program));
        given(starRepository.findByMemberAndProgram(MEMBER1, program)).willReturn(Optional.empty());

        // when
        StarResponse response = starService.saveStar(MEMBER1.getMemberId(), 1L);

        // then
        verify(starRepository, times(1)).save(any(Star.class));
        verify(starRepository, never()).delete(any(Star.class));
        assertThat(response.starStatus()).isEqualTo("save");
    }

    @Test
    @DisplayName("즐겨찾기가 이미 있으면 삭제한다")
    void saveStar_delete() {
        // given
        Program program = mock(Program.class);
        Star star = mock(Star.class);
        given(memberRepository.getByIdOrThrow(anyLong())).willReturn(MEMBER1);
        given(programRepository.findById(anyLong())).willReturn(Optional.of(program));
        given(starRepository.findByMemberAndProgram(MEMBER1, program)).willReturn(Optional.of(star));

        // when
        StarResponse response = starService.saveStar(MEMBER1.getMemberId(), 1L);

        // then
        verify(starRepository, times(1)).delete(star);
        verify(starRepository, never()).save(any(Star.class));
        assertThat(response.starStatus()).isEqualTo("delete");
    }

    @Test
    @DisplayName("즐겨찾기한 프로그램 ID들을 한 번의 조회로 반환한다")
    void findStarredProgramIds() {
        // given
        List<Long> programIds = List.of(1L, 2L, 3L);
        given(starRepository.findStarredProgramIds(MEMBER1.getMemberId(), programIds))
                .willReturn(Set.of(1L, 3L));

        // when
        Set<Long> result = starService.findStarredProgramIds(MEMBER1.getMemberId(), programIds);

        // then
        assertThat(result).containsExactlyInAnyOrder(1L, 3L);
        verify(starRepository, times(1)).findStarredProgramIds(MEMBER1.getMemberId(), programIds);
    }
}
