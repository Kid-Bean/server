package soongsil.kidbean.server.quiz.application;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER1;
import static soongsil.kidbean.server.quiz.fixture.ImageQuizFixture.IMAGE_QUIZ_ANIMAL1;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import soongsil.kidbean.server.global.application.S3Uploader;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.quiz.domain.ImageQuiz;
import soongsil.kidbean.server.quiz.domain.type.QuizCategory;
import soongsil.kidbean.server.quiz.dto.request.ImageQuizUploadRequest;
import soongsil.kidbean.server.quiz.dto.response.ImageQuizMemberDetailResponse;
import soongsil.kidbean.server.quiz.dto.response.ImageQuizMemberResponse;
import soongsil.kidbean.server.quiz.dto.response.ImageQuizSolveListResponse;
import soongsil.kidbean.server.quiz.repository.ImageQuizRepository;

@ExtendWith(MockitoExtension.class)
class ImageQuizServiceTest {

    @Mock
    private ImageQuizRepository imageQuizRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private S3Uploader s3Uploader;

    @InjectMocks
    private ImageQuizService imageQuizService;

    @Value("${cloud.aws.s3.bucket}")
    private final String COMMON_URL = "example.com";
    private final String folderName = "quiz/NONE";

    @Test
    @DisplayName("ImageQuizResponse 생성 테스트")
    void selectRandomImageQuiz() {
        //given
        given(memberRepository.findById(any(Long.class))).willReturn(Optional.of(MEMBER1));
        given(imageQuizRepository.countByMemberOrAdmin(any(Member.class))).willReturn(7);
        given(imageQuizRepository.findSinglePageByMember(any(Member.class), any(Pageable.class)))
                .willReturn(new PageImpl<>(List.of(IMAGE_QUIZ_ANIMAL1)));

        //when
        ImageQuizSolveListResponse imageQuizSolveResponse = imageQuizService.selectRandomImageQuizList(1L, 5);

        //then
        assertThat(imageQuizSolveResponse.imageQuizSolveResponseList().get(0).quizId())
                .isEqualTo(IMAGE_QUIZ_ANIMAL1.getQuizId());
    }

    @Test
    @DisplayName("quizId에 해당하는 ImageQuiz 반환")
    void getImageQuiz() {
        // given
        given(imageQuizRepository.findByQuizIdAndMember_MemberId(any(Long.class), eq(MEMBER1.getMemberId())))
                .willReturn(Optional.of(IMAGE_QUIZ_ANIMAL1));

        // when
        ImageQuizMemberDetailResponse response = imageQuizService.getImageQuizById(MEMBER1.getMemberId(), 1L);

        // then
        assertThat(response.answer()).isEqualTo(IMAGE_QUIZ_ANIMAL1.getAnswer());
    }

    @Test
    @DisplayName("특정 Member가 추가한 ImageQuiz 리스트 반환")
    void getAllImageQuizByMember() {
        List<ImageQuiz> imageQuizList = List.of(IMAGE_QUIZ_ANIMAL1);

        // given
        given(imageQuizRepository.findAllByMember_MemberId(MEMBER1.getMemberId())).willReturn(imageQuizList);

        // when
        List<ImageQuizMemberResponse> response = imageQuizService.getAllImageQuizByMember(MEMBER1.getMemberId());

        // then
        assertThat(response.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("ImageQuiz 등록하기")
    void uploadImageQuiz() {
        // given
        ImageQuizUploadRequest request = ImageQuizUploadRequest.builder()
                .title("테스트 제목")
                .answer("테스트 정답")
                .quizCategory(QuizCategory.NONE)
                .build();

        // MultipartFile 생성
        MockMultipartFile file = new MockMultipartFile(
                "file", // form의 input file name
                "filename.jpg", // 파일명
                "text/plain", // 컨텐츠 타입
                "This is the file content".getBytes() // 파일 컨텐츠
        );

        String s3Url = "https://kidbean.s3.ap-northeast-2.amazonaws.com/quiz/NONE/filename.jpg";

        given(memberRepository.findById(any(Long.class))).willReturn(Optional.of(MEMBER1));
        given(s3Uploader.upload(any(MultipartFile.class), anyString())).willReturn(s3Url);

        // when
        imageQuizService.uploadImageQuiz(request, 1L, file);

        // then
        verify(memberRepository, times(1)).findById(MEMBER1.getMemberId());
        verify(s3Uploader, times(1)).upload(any(MultipartFile.class), anyString());
        verify(imageQuizRepository, times(1)).save(any(ImageQuiz.class));
    }

    @Test
    @DisplayName("ImageQuiz 수정하기")
    void updateImageQuiz() {
        // given

        // when

        // then
    }
}