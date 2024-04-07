package soongsil.kidbean.server.quiz.util;

import static soongsil.kidbean.server.member.util.MemberInitializer.DUMMY_ADMIN;
import static soongsil.kidbean.server.member.util.MemberInitializer.DUMMY_MEMBER;
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

    @Override
    public void run(ApplicationArguments args) {
        if (imageQuizRepository.count() > 0) {
            log.info("[ImageQuiz]더미 데이터 존재");
        } else {
            //더미 데이터 작성
            List<ImageQuiz> imageQuizList = new ArrayList<>();

            imageQuizList.add(ImageQuiz.builder()
                    .quizCategory(ANIMAL)
                    .level(BRONZE)
                    .title("titleAnimal")
                    .answer("answerAnimal")
                    .s3Info(new S3Info("imageUrl", "filename", "folderName"))
                    .member(DUMMY_ADMIN)
                    .build());
            imageQuizList.add(ImageQuiz.builder()
                    .quizCategory(NONE)
                    .level(SILVER)
                    .title("titleNone")
                    .answer("answerNone")
                    .s3Info(new S3Info("imageUrl", "filename", "folderName"))
                    .member(DUMMY_ADMIN)
                    .build());
            imageQuizList.add(ImageQuiz.builder()
                    .quizCategory(OBJECT)
                    .level(SILVER)
                    .title("titleObject")
                    .answer("answerObject")
                    .s3Info(new S3Info("imageUrl", "filename", "folderName"))
                    .member(DUMMY_ADMIN)
                    .build());
            imageQuizList.add(ImageQuiz.builder()
                    .quizCategory(OBJECT)
                    .level(SILVER)
                    .title("titleObject2")
                    .answer("answerObject2")
                    .s3Info(new S3Info("imageUrl", "filename", "folderName"))
                    .member(DUMMY_MEMBER)
                    .build());
            imageQuizList.add(ImageQuiz.builder()
                    .quizCategory(PLANT)
                    .level(SILVER)
                    .title("titlePlant")
                    .answer("answerPlant")
                    .s3Info(new S3Info("imageUrl", "filename", "folderName"))
                    .member(DUMMY_MEMBER)
                    .build());
            imageQuizList.add(ImageQuiz.builder()
                    .quizCategory(PLANT)
                    .level(SILVER)
                    .title("titlePlant2")
                    .answer("answerPlant2")
                    .s3Info(new S3Info("imageUrl", "filename", "folderName"))
                    .member(DUMMY_ADMIN)
                    .build());

            imageQuizRepository.saveAll(imageQuizList);
        }
    }
}
