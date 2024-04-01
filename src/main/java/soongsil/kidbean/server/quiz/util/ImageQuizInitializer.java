package soongsil.kidbean.server.quiz.util;

import static soongsil.kidbean.server.member.util.MemberInitializer.DUMMY_ADMIN;
import static soongsil.kidbean.server.member.util.MemberInitializer.DUMMY_MEMBER;
import static soongsil.kidbean.server.quiz.domain.type.Category.*;
import static soongsil.kidbean.server.quiz.domain.type.Level.*;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import soongsil.kidbean.server.global.util.LocalDummyDataInit;
import soongsil.kidbean.server.global.vo.ImageInfo;
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
                    .category(ANIMAL)
                    .level(BRONZE)
                    .title("titleAnimal")
                    .answer("answerAnimal")
                    .imageInfo(new ImageInfo("imageUrl", "filename", "folderName"))
                    .member(DUMMY_ADMIN)
                    .build());
            imageQuizList.add(ImageQuiz.builder()
                    .category(NONE)
                    .level(SILVER)
                    .title("titleNone")
                    .answer("answerNone")
                    .imageInfo(new ImageInfo("imageUrl", "filename", "folderName"))
                    .member(DUMMY_ADMIN)
                    .build());
            imageQuizList.add(ImageQuiz.builder()
                    .category(OBJECT)
                    .level(SILVER)
                    .title("titleObject")
                    .answer("answerObject")
                    .imageInfo(new ImageInfo("imageUrl", "filename", "folderName"))
                    .member(DUMMY_ADMIN)
                    .build());
            imageQuizList.add(ImageQuiz.builder()
                    .category(OBJECT)
                    .level(SILVER)
                    .title("titleObject2")
                    .answer("answerObject2")
                    .imageInfo(new ImageInfo("imageUrl", "filename", "folderName"))
                    .member(DUMMY_MEMBER)
                    .build());
            imageQuizList.add(ImageQuiz.builder()
                    .category(PLANT)
                    .level(SILVER)
                    .title("titlePlant")
                    .answer("answerPlant")
                    .imageInfo(new ImageInfo("imageUrl", "filename", "folderName"))
                    .member(DUMMY_MEMBER)
                    .build());
            imageQuizList.add(ImageQuiz.builder()
                    .category(PLANT)
                    .level(SILVER)
                    .title("titlePlant2")
                    .answer("answerPlant2")
                    .imageInfo(new ImageInfo("imageUrl", "filename", "folderName"))
                    .member(DUMMY_ADMIN)
                    .build());

            imageQuizRepository.saveAll(imageQuizList);
        }
    }
}
