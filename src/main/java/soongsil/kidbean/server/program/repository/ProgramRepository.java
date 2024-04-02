package soongsil.kidbean.server.program.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.program.domain.Program;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {
    @Query("SELECT p.teacherName, p.title, p.place, p.content, " +
            "p.phoneNumber FROM Program p WHERE p.programId = :programId")
    List<Object[]> findProgramInfo(Long programId);

}
