package soongsil.kidbean.server.quiz.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static soongsil.kidbean.server.member.fixture.MemberFixture.ADMIN;
import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER1;
import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER2;
import static soongsil.kidbean.server.quiz.fixture.ImageQuizFixture.IMAGE_QUIZ_ANIMAL1;
import static soongsil.kidbean.server.quiz.fixture.ImageQuizFixture.IMAGE_QUIZ_ANIMAL2;
import static soongsil.kidbean.server.quiz.fixture.ImageQuizFixture.IMAGE_QUIZ_NONE;
import static soongsil.kidbean.server.quiz.fixture.ImageQuizFixture.IMAGE_QUIZ_OBJECT;
import static soongsil.kidbean.server.quiz.fixture.ImageQuizFixture.IMAGE_QUIZ_PLANT;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.domain.type.Role;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.quiz.domain.ImageQuiz;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
class ImageQuizRepositoryTest {

    @Autowired
    ImageQuizRepository imageQuizRepository;

    @Autowired
    MemberRepository memberRepository;

    @BeforeAll
    void setUp() {
        List<ImageQuiz> imageQuizList =
                List.of(IMAGE_QUIZ_ANIMAL1, IMAGE_QUIZ_ANIMAL2, IMAGE_QUIZ_PLANT, IMAGE_QUIZ_OBJECT, IMAGE_QUIZ_NONE);
        List<Member> memberList = List.of(MEMBER1, MEMBER2, ADMIN);

        memberRepository.saveAll(memberList);
        imageQuizRepository.saveAll(imageQuizList);
    }

    @Test
    @DisplayName("DB에 저장된 row 수 테스트")
    void countByMemberAndCategoryOrRole() {
        //given

        //when
        Integer totalCnt = imageQuizRepository.countByMemberAndCategoryOrRole(MEMBER1, Role.ADMIN);

        //then
        assertThat(totalCnt).isEqualTo(3);
    }

    @Test
    @DisplayName("페이지 생성이 제대로 동작 하는지 확인")
    void findImageQuizWithPage() {
        //given

        //when
        Page<ImageQuiz> imageQuizPage = imageQuizRepository.findImageQuizWithPage(
                MEMBER1, Role.ADMIN, PageRequest.of(0, 1));

        //then
        assertThat(imageQuizPage.getTotalPages()).isEqualTo(3);
    }
}