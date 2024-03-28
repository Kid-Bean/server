package soongsil.kidbean.server.quiz.util;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import soongsil.kidbean.server.quiz.domain.ImageQuiz;
import soongsil.kidbean.server.quiz.domain.type.Category;
import soongsil.kidbean.server.quiz.repository.ImageQuizRepository;

@RequiredArgsConstructor
@Component
public class ImageQuizInitializer implements ApplicationRunner {

    private final ImageQuizRepository imageQuizRepository;

    @Override
    public void run(ApplicationArguments args) {
        //더미 데이터 작성
        List<ImageQuiz> imageQuizList =
                List.of(new ImageQuiz(Category.ANIMAL, "answer1", "title1", null, null, null),
                        new ImageQuiz(Category.NONE, "answer2", "title2", null, null, null),
                        new ImageQuiz(Category.ANIMAL, "answer3", "title3", null, null, null),
                        new ImageQuiz(Category.OBJECT, "answer4", "title4", null, null, null),
                        new ImageQuiz(Category.PLANT, "answer5", "title5", null, null, null));

        imageQuizRepository.saveAll(imageQuizList);
    }
}
