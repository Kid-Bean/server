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
import soongsil.kidbean.server.quiz.domain.type.Category;
import soongsil.kidbean.server.quiz.dto.request.ImageQuizUploadRequest;
import soongsil.kidbean.server.quiz.dto.response.ImageQuizMemberDetailResponse;
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
    void ImageQuizResponse_생성_테스트() {
        //given
        List<ImageQuiz> imageQuizList = List.of(IMAGE_QUIZ_ANIMAL);
        Page<ImageQuiz> imageQuizPage = new PageImpl<>(imageQuizList);

        given(imageQuizRepository.findAllImageQuizWithPage(
                any(Member.class), any(Role.class), any(Category.class), any(Pageable.class)))
                .willReturn(imageQuizPage);
        given(imageQuizRepository.countByMemberAndCategory(any(Member.class), any(Category.class)))
                .willReturn(1);
        given(imageQuizRepository.countByMember_RoleAndCategory(any(Role.class), any(Category.class))).willReturn(0);
        given(memberRepository.findById(any(Long.class))).willReturn(Optional.of(MEMBER));

        //when
        ImageQuizResponse imageQuizResponse = imageQuizService.selectRandomProblem(1L);

        //then
        assertThat(imageQuizResponse.category()).isEqualTo(imageQuizList.get(0).getCategory().toString());
    }

    @Test
    @DisplayName("quizId에 해당하는 ImageQuiz 반환")
    void getImageQuiz() {
        // given
        given(memberRepository.findById(MEMBER.getMemberId()))
                .willReturn(Optional.of(MEMBER));
        given(imageQuizRepository.findByQuizIdAndMember(any(Long.class), eq(MEMBER)))
                .willReturn(Optional.of(IMAGE_QUIZ_ANIMAL));

        // when
        ImageQuizMemberDetailResponse response = imageQuizService.getImageQuizById(MEMBER.getMemberId(), 1L);

        // then
        assertThat(response.answer()).isEqualTo(IMAGE_QUIZ_ANIMAL.getAnswer());
    }
}