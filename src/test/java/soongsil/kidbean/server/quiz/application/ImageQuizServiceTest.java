package soongsil.kidbean.server.quiz.application;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static soongsil.kidbean.server.quiz.fixture.ImageQuizFixture.IMAGE_QUIZ_ANIMAL;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import soongsil.kidbean.server.quiz.domain.ImageQuiz;
import soongsil.kidbean.server.quiz.domain.type.Category;
import soongsil.kidbean.server.quiz.dto.response.ImageQuizResponse;
import soongsil.kidbean.server.quiz.repository.ImageQuizRepository;

@ExtendWith(MockitoExtension.class)
class ImageQuizServiceTest {

    @Mock
    private ImageQuizRepository imageQuizRepository;

    @InjectMocks
    private ImageQuizService imageQuizService;

    @Test
    void ImageQuizResponse_생성_테스트() {
        //given
        List<ImageQuiz> imageQuizList = List.of(IMAGE_QUIZ_ANIMAL);
        Page<ImageQuiz> imageQuizPage = new PageImpl<>(imageQuizList);

        given(imageQuizRepository.findAllByCategory(any(Category.class), any(Pageable.class)))
                .willReturn(imageQuizPage);
        given(imageQuizRepository.countByCategory(any(Category.class)))
                .willReturn(1);

        //when
        ImageQuizResponse imageQuizResponse = imageQuizService.selectRandomProblem();

        //then
        assertThat(imageQuizResponse.category()).isEqualTo(imageQuizList.get(0).getCategory().toString());
    }
}