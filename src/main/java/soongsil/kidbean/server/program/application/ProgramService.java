package soongsil.kidbean.server.program.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.program.domain.Program;
import soongsil.kidbean.server.program.dto.request.ProgramRequest;
import soongsil.kidbean.server.program.dto.response.ProgramResponse;
import soongsil.kidbean.server.program.repository.ProgramRepository;
import soongsil.kidbean.server.quiz.exception.MemberNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ProgramService {

    private final ProgramRepository programRepository;

    @Transactional
    public ProgramRequest getProgramList(Long programId) {
        Program program = programRepository.findById(programId)
                .orElseThrow(RuntimeException::new);

        ProgramRequest a = ProgramRequest.from(program);
        log.info("조회 기본 test value = {}", a);

       return ProgramRequest.from(program);
    }

    /* 프로그램 상세 조회 */
    @Transactional
    public ProgramResponse getProgramInfo(Long programId, String teacherName, String title,
                                          String place , String content, String phoneNumber) {
        Program program = programRepository.findById(programId).orElseThrow(RuntimeException::new);

        String programTeacherName = program.getTeacherName();
        String programTitle = program.getTitle();
        String programPlace = program.getPlace();
        String programContent = program.getContent();
        String programPhoneNumber = program.getPhoneNumber();


        log.info("프로그램 상세 조회 테스트 log = ", program);

        return ProgramResponse.from(program);
    }
}