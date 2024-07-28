package soongsil.kidbean.server.quizsolve.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import soongsil.kidbean.server.global.application.S3Uploader;
import soongsil.kidbean.server.quizsolve.application.vo.ApiResponseVO.ReturnObject.Sentence.MorphemeVO;
import soongsil.kidbean.server.quizsolve.application.vo.OpenApiResponse;
import soongsil.kidbean.server.quizsolve.application.vo.UseWordVO;
import soongsil.kidbean.server.quizsolve.domain.AnswerQuizSolved;
import soongsil.kidbean.server.quizsolve.repository.AnswerQuizSolvedRepository;
import soongsil.kidbean.server.quizsolve.repository.UseWordRepository;

import java.util.List;

import static org.mockito.Mockito.*;
import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER1;
import static soongsil.kidbean.server.answerquiz.fixture.AnswerQuizFixture.ANSWER_QUIZ;

@ExtendWith(MockitoExtension.class)
class AnswerQuizSolvedServiceTest {

    @Mock
    private UseWordRepository useWordRepository;
    @Mock
    private AnswerQuizSolvedRepository answerQuizSolvedRepository;
    @Mock
    private S3Uploader s3Uploader;

    @InjectMocks
    private AnswerQuizSolvedService answerQuizSolvedService;

    @Test
    @DisplayName("AnswerQuizSolved 등록")
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

        //when
        answerQuizSolvedService.enrollNewAnswerQuizSolved(
                ANSWER_QUIZ, submitAnswer, MEMBER1, openApiResponse, multipartFile);

        //then
        verify(answerQuizSolvedRepository).save(any(AnswerQuizSolved.class));
        verify(useWordRepository, atLeastOnce()).saveAll(anyList());
    }
}
