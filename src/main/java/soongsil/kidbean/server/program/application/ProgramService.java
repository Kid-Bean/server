package soongsil.kidbean.server.program.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.program.domain.Program;
import soongsil.kidbean.server.program.domain.type.ProgramCategory;
import soongsil.kidbean.server.program.dto.response.ProgramListResponse;
import soongsil.kidbean.server.program.dto.response.ProgramDetailResponse;
import soongsil.kidbean.server.program.repository.ProgramRepository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ProgramService {

    private final ProgramRepository programRepository;

    /**
     * 프로그램 상세 조회
     *
     * @param programId 프로그램 id
     * @return response
     */
    @Transactional
    public ProgramDetailResponse getProgramInfo(Long programId) {

        Program program = programRepository.findById(programId)
                .orElseThrow(RuntimeException::new);

        return ProgramDetailResponse.from(program);
    }

    /* 카테고리에 따른 프로그램 목록 조회 */
    @Transactional
    public ProgramListResponse getProgramListInfo(ProgramCategory programCategory) {
        List<Program> programList = programRepository.findAllByProgramCategory(programCategory);
        return ProgramListResponse.from(programList);
    }
}