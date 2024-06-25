package soongsil.kidbean.server.quiz.application;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import soongsil.kidbean.server.global.application.S3Uploader;
import soongsil.kidbean.server.global.domain.S3Info;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.fixture.MemberFixture;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.quiz.domain.ImageQuiz;
import soongsil.kidbean.server.quiz.domain.type.QuizCategory;
import soongsil.kidbean.server.quiz.dto.request.ImageQuizUpdateRequest;
import soongsil.kidbean.server.quiz.dto.request.ImageQuizUploadRequest;
import soongsil.kidbean.server.quiz.dto.response.ImageQuizMemberDetailResponse;
import soongsil.kidbean.server.quiz.dto.response.ImageQuizMemberResponse;
import soongsil.kidbean.server.quiz.dto.response.ImageQuizSolveListResponse;
import soongsil.kidbean.server.quiz.repository.ImageQuizRepository;

import java.util.List;
import java.util.Optional;
import soongsil.kidbean.server.quiz.util.RandNumUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER1;
import static soongsil.kidbean.server.quiz.fixture.ImageQuizFixture.IMAGE_QUIZ_ANIMAL1;

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

    @Test
    @DisplayName("ImageQuizResponse 생성 테스트")
    void selectRandomImageQuiz() {
        //given
        int IMAGE_QUIZ_SIZE = 5;
        int IMAGE_QUIZ_TOTAL_NUM = 10;

        given(memberRepository.findById(MEMBER1.getMemberId())).willReturn(Optional.of(MEMBER1));
        given(imageQuizRepository.countByMemberOrAdmin(MEMBER1)).willReturn(IMAGE_QUIZ_TOTAL_NUM);
        given(imageQuizRepository.findByMemberAndQuizIdIn(eq(MEMBER1), anyList())).willReturn(
                List.of(IMAGE_QUIZ_ANIMAL1));

        //when
        ImageQuizSolveListResponse imageQuizSolveResponse =
                imageQuizService.selectRandomImageQuizList(MEMBER1.getMemberId(), IMAGE_QUIZ_SIZE);

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
        assertThat(response).hasSize(1);
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

        given(memberRepository.findById(any(Long.class))).willReturn(Optional.of(MEMBER1));
        given(s3Uploader.upload(any(MultipartFile.class), anyString())).willReturn(any(S3Info.class));

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
        ImageQuizUpdateRequest request = ImageQuizUpdateRequest.builder()
                .title("테스트 제목 수정")
                .answer("테스트 정답 수정")
                .quizCategory(QuizCategory.NONE)
                .build();

        // MultipartFile 생성
        MockMultipartFile file = new MockMultipartFile(
                "file", // form의 input file name
                "filename.jpg", // 파일명
                "text/plain", // 컨텐츠 타입
                "This is the file content".getBytes() // 파일 컨텐츠
        );

        S3Info s3Info = S3Info.builder()
                .s3Url("https://example.com/quiz/NONE/filename.jpg")
                .fileName("filename.jpg")
                .folderName("quiz/NONE")
                .build();

        ImageQuiz imageQuiz = mock(ImageQuiz.class);

        given(imageQuizRepository.findById(IMAGE_QUIZ_ANIMAL1.getQuizId())).willReturn(Optional.of(imageQuiz));
        given(imageQuiz.getS3Info()).willReturn(s3Info);
        given(s3Uploader.upload(any(MultipartFile.class), anyString())).willReturn(s3Info);

        // when
        imageQuizService.updateImageQuiz(request, IMAGE_QUIZ_ANIMAL1.getQuizId(), file);

        // then
        verify(imageQuizRepository).findById(IMAGE_QUIZ_ANIMAL1.getQuizId());
        verify(s3Uploader, times(1)).upload(any(MultipartFile.class), anyString());
        verify(s3Uploader).deleteFile(s3Info);
    }

    @Test
    @DisplayName("ImageQuiz 삭제하기")
    void deleteImageQuiz() {
        // given
        given(imageQuizRepository.findById(IMAGE_QUIZ_ANIMAL1.getQuizId())).willReturn(Optional.of(IMAGE_QUIZ_ANIMAL1));

        // when
        imageQuizService.deleteImageQuiz(IMAGE_QUIZ_ANIMAL1.getQuizId());

        // then
        verify(imageQuizRepository).delete(IMAGE_QUIZ_ANIMAL1);
    }
}