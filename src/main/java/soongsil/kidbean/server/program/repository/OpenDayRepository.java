package soongsil.kidbean.server.program.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soongsil.kidbean.server.program.domain.OpenDay;
import soongsil.kidbean.server.program.domain.Program;

import java.util.List;

public interface OpenDayRepository extends JpaRepository<OpenDay, Long> {

    List<OpenDay> findAllByProgram(Program program);
}
