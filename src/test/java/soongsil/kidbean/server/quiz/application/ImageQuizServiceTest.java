package soongsil.kidbean.server.quiz.application;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER;
import static soongsil.kidbean.server.quiz.fixture.ImageQuizFixture.IMAGE_QUIZ_ANIMAL;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.domain.type.Role;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.quiz.domain.ImageQuiz;
import soongsil.kidbean.server.quiz.domain.type.QuizCategory;
import soongsil.kidbean.server.quiz.dto.response.ImageQuizMemberDetailResponse;
import soongsil.kidbean.server.quiz.dto.response.ImageQuizMemberResponse;
import soongsil.kidbean.server.quiz.dto.response.ImageQuizResponse;
import soongsil.kidbean.server.quiz.repository.ImageQuizRepository;

@ExtendWith(MockitoExtension.class)
class ImageQuizServiceTest {

    @Mock
    private ImageQuizRepository imageQuizRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private ImageQuizService imageQuizService;

    @Test
    @DisplayName("ImageQuizResponse 생성 테스트")
    void selectRandomImageQuiz() {
        //given
        List<ImageQuiz> imageQuizList = List.of(IMAGE_QUIZ_ANIMAL);
        Page<ImageQuiz> imageQuizPage = new PageImpl<>(imageQuizList);

        given(imageQuizRepository.findImageQuizWithPage(
                any(Member.class), any(Role.class), any(QuizCategory.class), any(Pageable.class)))
                .willReturn(imageQuizPage);
        given(imageQuizRepository.countByMemberAndCategoryOrRole(
                any(Member.class), any(QuizCategory.class), any(Role.class))).willReturn(1);
        given(memberRepository.findById(any(Long.class))).willReturn(Optional.of(MEMBER));

        //when
        ImageQuizResponse imageQuizResponse = imageQuizService.selectRandomImageQuiz(1L);

        //then
        assertThat(imageQuizResponse.quizId()).isEqualTo(imageQuizList.get(0).getQuizId());
    }

    @Test
    @DisplayName("quizId에 해당하는 ImageQuiz 반환")
    void getImageQuiz() {
        // given
        given(imageQuizRepository.findByQuizIdAndMember_MemberId(any(Long.class), eq(MEMBER.getMemberId())))
                .willReturn(Optional.of(IMAGE_QUIZ_ANIMAL));

        // when
        ImageQuizMemberDetailResponse response = imageQuizService.getImageQuizById(MEMBER.getMemberId(), 1L);

        // then
        assertThat(response.answer()).isEqualTo(IMAGE_QUIZ_ANIMAL.getAnswer());
    }

    @Test
    @DisplayName("특정 Member가 추가한 ImageQuiz 리스트 반환")
    void getAllImageQuizByMember() {
        List<ImageQuiz> imageQuizList = List.of(IMAGE_QUIZ_ANIMAL);

        // given
        given(imageQuizRepository.findAllByMember_MemberId(MEMBER.getMemberId())).willReturn(imageQuizList);

        // when
        List<ImageQuizMemberResponse> response = imageQuizService.getAllImageQuizByMember(MEMBER.getMemberId());

        // then
        assertThat(response.size()).isEqualTo(1);
    }
}