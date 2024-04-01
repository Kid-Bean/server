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

import java.util.Optional;
import soongsil.kidbean.server.quiz.domain.type.Category;

@Repository
public interface ImageQuizRepository extends JpaRepository<ImageQuiz, Long> {

    //해당 카테고리의 row 개수
    @Query("SELECT count(*) FROM ImageQuiz iq WHERE iq.category = :category AND (iq.member = :member OR iq.member.role = :role)")
    Integer countByMemberAndCategoryOrRoleIsAdmin(Member member, Category category, Role role);

    @Query("SELECT iq FROM ImageQuiz iq WHERE iq.category = :category AND (iq.member = :member OR iq.member.role = :role)")
    Page<ImageQuiz> findAllImageQuizWithPage(@Param("member") Member member, @Param("role") Role role,
                                             @Param("category") Category category, Pageable pageable);

    Optional<ImageQuiz> findByQuizIdAndMember(Long quizId, Member member);
}
