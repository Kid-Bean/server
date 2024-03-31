package soongsil.kidbean.server.quiz.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quiz.domain.ImageQuizSolved;

public interface ImageQuizSolvedRepository extends JpaRepository<ImageQuizSolved, Long> {

    List<ImageQuizSolved> findAllByMember(Member member);
}
