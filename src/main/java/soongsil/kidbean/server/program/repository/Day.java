package soongsil.kidbean.server.program.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Day extends JpaRepository<Day, Long> {
   // List 로 받기
}
