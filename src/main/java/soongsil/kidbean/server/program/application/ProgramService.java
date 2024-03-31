package soongsil.kidbean.server.program.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import soongsil.kidbean.server.program.domain.Program;
import soongsil.kidbean.server.program.dto.ProgramRequest;
import soongsil.kidbean.server.program.repository.ProgramRepository;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProgramService {

    private final ProgramRepository programRepository;

    public ProgramRequest getProgramsList(Long programId) {
        Program program = programRepository.findById(programId)
                .orElseThrow(RuntimeException::new);

        ProgramRequest a = ProgramRequest.from(program);
        log.info("a value = {}", a);

       return ProgramRequest.from(program);
    }
}