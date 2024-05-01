package soongsil.kidbean.server.quiz.repository.init;

import static soongsil.kidbean.server.member.repository.init.MemberInitializer.DUMMY_ADMIN;
import static soongsil.kidbean.server.member.repository.init.MemberInitializer.DUMMY_MEMBER;
import static soongsil.kidbean.server.quiz.domain.type.QuizCategory.*;
import static soongsil.kidbean.server.quiz.domain.type.Level.*;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import soongsil.kidbean.server.global.util.LocalDummyDataInit;
import soongsil.kidbean.server.global.vo.S3Info;
import soongsil.kidbean.server.quiz.domain.ImageQuiz;
import soongsil.kidbean.server.quiz.repository.ImageQuizRepository;

@Slf4j
@RequiredArgsConstructor
@Order(2)
@LocalDummyDataInit
public class ImageQuizInitializer implements ApplicationRunner {

    private final ImageQuizRepository imageQuizRepository;

    public static final ImageQuiz IMAGE_QUIZ_1 = ImageQuiz.builder()
            .quizCategory(ANIMAL)
            .level(BRONZE)
            .title("rabbit")
            .answer("토끼")
            .s3Info(S3Info.builder()
                    .folderName("quiz/ANIMAL")
                    .fileName("rabbit.jpg")
                    .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/quiz/ANIMAL/rabbit.jpg")
                    .build()
            )
            .member(DUMMY_ADMIN)
            .build();

    public static final ImageQuiz IMAGE_QUIZ_2 = ImageQuiz.builder()
            .quizCategory(NONE)
            .level(SILVER)
            .title("car")
            .answer("자동차")
            .s3Info(S3Info.builder()
                    .folderName("quiz/NONE")
                    .fileName("car.jpg")
                    .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/quiz/NONE/car.jpg")
                    .build()
            )
            .member(DUMMY_ADMIN)
            .build();

    public static final ImageQuiz IMAGE_QUIZ_3 = ImageQuiz.builder()
            .quizCategory(NONE)
            .level(SILVER)
            .title("tissue")
            .answer("화장지")
            .s3Info(S3Info.builder()
                    .folderName("quiz/NONE")
                    .fileName("tissue.jpg")
                    .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/quiz/NONE/tissue.jpg")
                    .build()
            )
            .member(DUMMY_ADMIN)
            .build();
    public static final ImageQuiz IMAGE_QUIZ_4 = ImageQuiz.builder()
            .quizCategory(OBJECT)
            .level(SILVER)
            .title("cap")
            .answer("모자")
            .s3Info(S3Info.builder()
                    .folderName("quiz/OBJECT")
                    .fileName("cap.jpg")
                    .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/quiz/OBJECT/cap.jpg")
                    .build()
            )
            .member(DUMMY_MEMBER)
            .build();
    public static final ImageQuiz IMAGE_QUIZ_5 = ImageQuiz.builder()
            .quizCategory(PLANT)
            .level(SILVER)
            .title("cactus")
            .answer("선인장")
            .s3Info(S3Info.builder()
                    .folderName("quiz/PLANT")
                    .fileName("cactus.jpg")
                    .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/quiz/PLANT/cactus.jpg")
                    .build()
            )
            .member(DUMMY_ADMIN)
            .build();

    public static final ImageQuiz IMAGE_QUIZ_6 = ImageQuiz.builder()
            .quizCategory(OBJECT)
            .level(SILVER)
            .title("eraser")
            .answer("지우개")
            .s3Info(S3Info.builder()
                    .folderName("quiz/OBJECT")
                    .fileName("eraser.jpg")
                    .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/quiz/OBJECT/eraser.jpg")
                    .build()
            )
            .member(DUMMY_ADMIN)
            .build();

    @Override
    public void run(ApplicationArguments args) {
        if (imageQuizRepository.count() > 0) {
            log.info("[ImageQuiz]더미 데이터 존재");
        } else {
            //더미 데이터 작성
            List<ImageQuiz> imageQuizList = new ArrayList<>();

            imageQuizList.add(IMAGE_QUIZ_1);
            imageQuizList.add(IMAGE_QUIZ_2);
            imageQuizList.add(IMAGE_QUIZ_3);
            imageQuizList.add(IMAGE_QUIZ_4);
            imageQuizList.add(IMAGE_QUIZ_5);
            imageQuizList.add(IMAGE_QUIZ_6);

            imageQuizRepository.saveAll(imageQuizList);
        }
    }
}
