package soongsil.kidbean.server.program.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soongsil.kidbean.server.program.domain.Day;
import soongsil.kidbean.server.program.domain.Program;

import java.util.List;

public interface DayRepository extends JpaRepository<Day, Long> {

    List<Day> findAllByProgram(Program program);
}
