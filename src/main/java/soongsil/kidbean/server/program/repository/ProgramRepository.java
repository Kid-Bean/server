package soongsil.kidbean.server.program.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.program.domain.Program;
import soongsil.kidbean.server.program.domain.type.ProgramCategory;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {

    Page<Program> findAllByProgramInfo_ProgramCategoryIn(List<ProgramCategory> programCategoryList, Pageable pageable);

}