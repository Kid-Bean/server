package soongsil.kidbean.server.quiz.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.domain.type.Role;
import soongsil.kidbean.server.quiz.domain.ImageQuiz;

import java.util.List;
import java.util.Optional;
import soongsil.kidbean.server.quiz.domain.type.QuizCategory;

@Repository
public interface ImageQuizRepository extends JpaRepository<ImageQuiz, Long> {

    //해당 카테고리의 row 개수
    @Query("SELECT count(*) FROM ImageQuiz iq WHERE iq.quizCategory = :category AND (iq.member = :member OR iq.member.role = :role)")
    Integer countByMemberAndCategoryOrRole(Member member, QuizCategory category, Role role);

    @Query("SELECT iq FROM ImageQuiz iq WHERE iq.quizCategory = :category AND (iq.member = :member OR iq.member.role = :role)")
    Page<ImageQuiz> findImageQuizWithPage(@Param("member") Member member, @Param("role") Role role,
                                          @Param("category") QuizCategory category, Pageable pageable);

    Optional<ImageQuiz> findByQuizIdAndMember(Long quizId, Member member);

    List<ImageQuiz> findAllByMember(Member member);
}
