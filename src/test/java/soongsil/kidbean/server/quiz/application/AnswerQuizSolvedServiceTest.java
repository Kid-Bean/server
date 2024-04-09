package soongsil.kidbean.server.quiz.application;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import soongsil.kidbean.server.global.application.S3Uploader;
import soongsil.kidbean.server.quiz.application.vo.MorphemeVO;
import soongsil.kidbean.server.quiz.application.vo.OpenApiResponse;
import soongsil.kidbean.server.quiz.application.vo.UseWordVO;
import soongsil.kidbean.server.quiz.domain.AnswerQuizSolved;
import soongsil.kidbean.server.quiz.domain.Morpheme;
import soongsil.kidbean.server.quiz.domain.UseWord;
import soongsil.kidbean.server.quiz.repository.AnswerQuizSolvedRepository;
import soongsil.kidbean.server.quiz.repository.MorphemeRepository;
import soongsil.kidbean.server.quiz.repository.UseWordRepository;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER;
import static soongsil.kidbean.server.quiz.fixture.AnswerQuizFixture.ANSWER_QUIZ;

@ExtendWith(MockitoExtension.class)
class AnswerQuizSolvedServiceTest {

    @Mock
    private MorphemeRepository morphemeRepository;
    @Mock
    private UseWordRepository useWordRepository;
    @Mock
    private AnswerQuizSolvedRepository answerQuizSolvedRepository;
    @Mock
    private S3Uploader s3Uploader;

    @InjectMocks
    private AnswerQuizSolvedService answerQuizSolvedService;

    @Test
    void testEnrollNewAnswerQuizSolved() {
        //given
        String submitAnswer = "제출된 정답";
        String fileName = "test.png";
        String contentType = "image/png";
        String folderName = "record/1";

        MockMultipartFile multipartFile = new MockMultipartFile("test", fileName, contentType, "test".getBytes());
        OpenApiResponse openApiResponse = OpenApiResponse.builder()
                .morphemeVOList(List.of(new MorphemeVO("엄마", "NNB")))
                .useWordVOList(List.of(new UseWordVO("엄마", 1L)))
                .build();

        given(s3Uploader.upload(any(MultipartFile.class), anyString())).willReturn(
                "s3://" + folderName + "/" + fileName);

        //when
        answerQuizSolvedService.enrollNewAnswerQuizSolved(
                ANSWER_QUIZ, submitAnswer, MEMBER, openApiResponse, multipartFile);

        //then
        verify(answerQuizSolvedRepository).save(any(AnswerQuizSolved.class));
        verify(morphemeRepository, atLeastOnce()).save(any(Morpheme.class));
        verify(useWordRepository, atLeastOnce()).save(any(UseWord.class));
    }
}
