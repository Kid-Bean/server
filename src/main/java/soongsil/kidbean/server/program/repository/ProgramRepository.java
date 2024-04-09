package soongsil.kidbean.server.program.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.program.domain.Program;
import soongsil.kidbean.server.program.domain.type.ProgramCategory;

import java.util.List;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {

    List<Program> findAllByProgramCategory(ProgramCategory programCategory);
}