package soongsil.kidbean.server.quiz.util;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import soongsil.kidbean.server.global.vo.ImageInfo;
import soongsil.kidbean.server.quiz.domain.ImageQuiz;
import soongsil.kidbean.server.quiz.domain.type.Category;
import soongsil.kidbean.server.quiz.repository.ImageQuizRepository;

@Slf4j
@RequiredArgsConstructor
@Profile("dev")
@Component
public class ImageQuizInitializer implements ApplicationRunner {

    private final ImageQuizRepository imageQuizRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (imageQuizRepository.count() > 0) {
            log.info("더미 데이터 존재");
        } else {
            //더미 데이터 작성
            List<ImageQuiz> imageQuizList = List.of(
                    new ImageQuiz(Category.ANIMAL, "answer1", "title1", new ImageInfo("asd1", null, null), null, null),
                    new ImageQuiz(Category.NONE, "answer2", "title2", new ImageInfo("asd2", null, null), null, null),
                    new ImageQuiz(Category.ANIMAL, "answer3", "title3", new ImageInfo("asd3", null, null), null, null),
                    new ImageQuiz(Category.OBJECT, "answer4", "title4", new ImageInfo("asd4", null, null), null, null),
                    new ImageQuiz(Category.PLANT, "answer5", "title5", new ImageInfo("asd5", null, null), null, null));

            imageQuizRepository.saveAll(imageQuizList);
        }
    }
}
