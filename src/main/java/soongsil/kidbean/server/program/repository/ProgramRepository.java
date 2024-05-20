package soongsil.kidbean.server.program.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.program.domain.Program;
import soongsil.kidbean.server.program.domain.type.ProgramCategory;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {

    Page<Program> findAllByProgramInfo_ProgramCategoryIn(List<ProgramCategory> programCategoryList, Pageable pageable);

    @Query("SELECT p "
            + "FROM Program p JOIN p.programInfo pi JOIN Star s ON p.programId = s.program.programId "
            + "WHERE s.member = :member")
    Page<Program> findAllStarProgramByProgramInfo(Pageable pageable, Member member);

}