package soongsil.kidbean.server.quiz.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import soongsil.kidbean.server.quiz.domain.ImageQuiz;
import soongsil.kidbean.server.quiz.domain.type.Category;

@Slf4j
@DataJpaTest
class ImageQuizRepositoryTest {

    @Autowired
    ImageQuizRepository imageQuizRepository;

    @BeforeEach
    void setUp() {
        List<ImageQuiz> imageQuizList =
                List.of(new ImageQuiz(Category.ANIMAL, "answer1", "title1", null, null, null),
                        new ImageQuiz(Category.NONE, "answer2", "title2", null, null, null),
                        new ImageQuiz(Category.ANIMAL, "answer3", "title3", null, null, null),
                        new ImageQuiz(Category.OBJECT, "answer4", "title4", null, null, null),
                        new ImageQuiz(Category.PLANT, "answer5", "title5", null, null, null));

        imageQuizRepository.saveAll(imageQuizList);
    }

    @Test
    void DB에_저장된_row의_수_테스트() {
        //given

        //when
        Integer animalCnt = imageQuizRepository.countByCategory(Category.ANIMAL);
        Integer plantCnt = imageQuizRepository.countByCategory(Category.PLANT);
        Integer objectCnt = imageQuizRepository.countByCategory(Category.OBJECT);
        Integer noneCnt = imageQuizRepository.countByCategory(Category.NONE);

        //then
        assertThat(animalCnt).isEqualTo(2);
        assertThat(plantCnt).isEqualTo(1);
        assertThat(objectCnt).isEqualTo(1);
        assertThat(noneCnt).isEqualTo(1);
    }

    @Test
    void findAllByCategory이_제대로_동작하는지_확인() {
        //given
        Category category = Category.ANIMAL;

        //when
        Page<ImageQuiz> imageQuizPage =
                imageQuizRepository.findAllByCategory(category, PageRequest.of(0, 1));

        //then
        assertThat(imageQuizPage.getTotalPages()).isEqualTo(2);
    }
}