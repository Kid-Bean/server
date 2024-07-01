package soongsil.kidbean.server.imagequiz.repository.init;

import static soongsil.kidbean.server.quizsolve.domain.type.QuizCategory.*;
import static soongsil.kidbean.server.quizsolve.domain.type.Level.*;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import soongsil.kidbean.server.global.util.LocalDummyDataInit;
import soongsil.kidbean.server.global.domain.S3Info;
import soongsil.kidbean.server.imagequiz.domain.ImageQuiz;
import soongsil.kidbean.server.imagequiz.repository.ImageQuizRepository;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.repository.MemberRepository;

@Slf4j
@RequiredArgsConstructor
@Order(2)
@LocalDummyDataInit
public class ImageQuizInitializer implements ApplicationRunner {

    private final MemberRepository memberRepository;
    private final ImageQuizRepository imageQuizRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (imageQuizRepository.count() > 0) {
            log.info("[ImageQuiz]더미 데이터 존재");
        } else {
            Member DUMMY_ADMIN = memberRepository.findById(2L).orElseThrow();
            Member DUMMY_MEMBER = memberRepository.findById(1L).orElseThrow();

            //더미 데이터 작성
            List<ImageQuiz> imageQuizList = new ArrayList<>();

            for (int i = 0; i < 1; i++) {

                ImageQuiz IMAGE_QUIZ_ANIMAL1 = ImageQuiz.builder()
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
                        .isDefault(true)
                        .build();

                ImageQuiz IMAGE_QUIZ_ANIMAL2 = ImageQuiz.builder()
                        .quizCategory(ANIMAL)
                        .level(SILVER)
                        .title("bee")
                        .answer("꿀벌")
                        .s3Info(S3Info.builder()
                                .folderName("quiz/ANIMAL")
                                .fileName("bee.jpg")
                                .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/quiz/ANIMAL/bee.jpg")
                                .build()
                        )
                        .member(DUMMY_ADMIN)
                        .isDefault(true)
                        .build();

                ImageQuiz IMAGE_QUIZ_ANIMAL3 = ImageQuiz.builder()
                        .quizCategory(ANIMAL)
                        .level(GOLD)
                        .title("fish")
                        .answer("물고기")
                        .s3Info(S3Info.builder()
                                .folderName("quiz/ANIMAL")
                                .fileName("fish.jpg")
                                .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/quiz/ANIMAL/fish.jpg")
                                .build()
                        )
                        .member(DUMMY_ADMIN)
                        .isDefault(true)
                        .build();

                ImageQuiz IMAGE_QUIZ_ANIMAL4 = ImageQuiz.builder()
                        .quizCategory(ANIMAL)
                        .level(PLATINUM)
                        .title("giraffe")
                        .answer("기린")
                        .s3Info(S3Info.builder()
                                .folderName("quiz/ANIMAL")
                                .fileName("giraffe.png")
                                .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/quiz/ANIMAL/giraffe.png")
                                .build()
                        )
                        .member(DUMMY_ADMIN)
                        .isDefault(true)
                        .build();

                ImageQuiz IMAGE_QUIZ_ANIMAL5 = ImageQuiz.builder()
                        .quizCategory(ANIMAL)
                        .level(DIAMOND)
                        .title("lion")
                        .answer("사자")
                        .s3Info(S3Info.builder()
                                .folderName("quiz/ANIMAL")
                                .fileName("lion.jpg")
                                .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/quiz/ANIMAL/lion.jpg")
                                .build()
                        )
                        .member(DUMMY_ADMIN)
                        .isDefault(true)
                        .build();

                ImageQuiz IMAGE_QUIZ_ANIMAL6 = ImageQuiz.builder()
                        .quizCategory(ANIMAL)
                        .level(DIAMOND)
                        .title("pig")
                        .answer("돼지")
                        .s3Info(S3Info.builder()
                                .folderName("quiz/ANIMAL")
                                .fileName("pig.jpg")
                                .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/quiz/ANIMAL/pig.jpg")
                                .build()
                        )
                        .member(DUMMY_MEMBER)
                        .isDefault(false)
                        .build();

                ImageQuiz IMAGE_QUIZ_ANIMAL7 = ImageQuiz.builder()
                        .quizCategory(ANIMAL)
                        .level(DIAMOND)
                        .title("rhino")
                        .answer("코뿔소")
                        .s3Info(S3Info.builder()
                                .folderName("quiz/ANIMAL")
                                .fileName("rhino.png")
                                .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/quiz/ANIMAL/rhino.png")
                                .build()
                        )
                        .member(DUMMY_MEMBER)
                        .isDefault(false)
                        .build();

                ImageQuiz IMAGE_QUIZ_FOOD1 = ImageQuiz.builder()
                        .quizCategory(FOOD)
                        .level(BRONZE)
                        .title("hamburger")
                        .answer("햄버거")
                        .s3Info(S3Info.builder()
                                .folderName("quiz/FOOD")
                                .fileName("hamburger.jpg")
                                .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/quiz/FOOD/hamburger.jpg")
                                .build()
                        )
                        .member(DUMMY_ADMIN)
                        .isDefault(true)
                        .build();

                ImageQuiz IMAGE_QUIZ_FOOD2 = ImageQuiz.builder()
                        .quizCategory(FOOD)
                        .level(SILVER)
                        .title("pancake")
                        .answer("팬케이크")
                        .s3Info(S3Info.builder()
                                .folderName("quiz/FOOD")
                                .fileName("pancake.jpg")
                                .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/quiz/FOOD/pancake.jpg")
                                .build()
                        )
                        .member(DUMMY_ADMIN)
                        .isDefault(true)
                        .build();

                ImageQuiz IMAGE_QUIZ_FOOD3 = ImageQuiz.builder()
                        .quizCategory(FOOD)
                        .level(GOLD)
                        .title("pizza")
                        .answer("피자")
                        .s3Info(S3Info.builder()
                                .folderName("quiz/FOOD")
                                .fileName("pizza.png")
                                .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/quiz/FOOD/pizza.png")
                                .build()
                        )
                        .member(DUMMY_ADMIN)
                        .isDefault(true)
                        .build();

                ImageQuiz IMAGE_QUIZ_FOOD4 = ImageQuiz.builder()
                        .quizCategory(FOOD)
                        .level(BRONZE)
                        .title("sushi")
                        .answer("초밥")
                        .s3Info(S3Info.builder()
                                .folderName("quiz/FOOD")
                                .fileName("sushi.jpg")
                                .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/quiz/FOOD/sushi.jpg")
                                .build()
                        )
                        .member(DUMMY_ADMIN)
                        .isDefault(true)
                        .build();

                ImageQuiz IMAGE_QUIZ_FOOD5 = ImageQuiz.builder()
                        .quizCategory(FOOD)
                        .level(BRONZE)
                        .title("dumpling")
                        .answer("만두")
                        .s3Info(S3Info.builder()
                                .folderName("quiz/FOOD")
                                .fileName("dumpling.jpg")
                                .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/quiz/FOOD/dumpling.jpg")
                                .build()
                        )
                        .member(DUMMY_MEMBER)
                        .isDefault(false)
                        .build();

                ImageQuiz IMAGE_QUIZ_NONE1 = ImageQuiz.builder()
                        .quizCategory(NONE)
                        .level(BRONZE)
                        .title("tissue")
                        .answer("화장지")
                        .s3Info(S3Info.builder()
                                .folderName("quiz/NONE")
                                .fileName("tissue.jpg")
                                .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/quiz/NONE/tissue.jpg")
                                .build()
                        )
                        .member(DUMMY_ADMIN)
                        .isDefault(true)
                        .build();

                ImageQuiz IMAGE_QUIZ_NONE2 = ImageQuiz.builder()
                        .quizCategory(NONE)
                        .level(SILVER)
                        .title("hand")
                        .answer("손")
                        .s3Info(S3Info.builder()
                                .folderName("quiz/NONE")
                                .fileName("hand.jpg")
                                .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/quiz/NONE/hand.jpg")
                                .build()
                        )
                        .member(DUMMY_ADMIN)
                        .isDefault(true)
                        .build();

                ImageQuiz IMAGE_QUIZ_NONE3 = ImageQuiz.builder()
                        .quizCategory(NONE)
                        .level(GOLD)
                        .title("chair")
                        .answer("의자")
                        .s3Info(S3Info.builder()
                                .folderName("quiz/NONE")
                                .fileName("chair.jpg")
                                .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/quiz/NONE/chair.jpg")
                                .build()
                        )
                        .member(DUMMY_ADMIN)
                        .isDefault(true)
                        .build();

                ImageQuiz IMAGE_QUIZ_NONE4 = ImageQuiz.builder()
                        .quizCategory(NONE)
                        .level(PLATINUM)
                        .title("doctor")
                        .answer("의사")
                        .s3Info(S3Info.builder()
                                .folderName("quiz/NONE")
                                .fileName("doctor.png")
                                .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/quiz/NONE/doctor.png")
                                .build()
                        )
                        .member(DUMMY_ADMIN)
                        .isDefault(true)
                        .build();

                ImageQuiz IMAGE_QUIZ_NONE5 = ImageQuiz.builder()
                        .quizCategory(NONE)
                        .level(DIAMOND)
                        .title("foot")
                        .answer("발")
                        .s3Info(S3Info.builder()
                                .folderName("quiz/NONE")
                                .fileName("foot.jpg")
                                .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/quiz/NONE/foot.jpg")
                                .build()
                        )
                        .member(DUMMY_ADMIN)
                        .isDefault(true)
                        .build();

                ImageQuiz IMAGE_QUIZ_NONE6 = ImageQuiz.builder()
                        .quizCategory(NONE)
                        .level(PLATINUM)
                        .title("car")
                        .answer("자동차")
                        .s3Info(S3Info.builder()
                                .folderName("quiz/NONE")
                                .fileName("car.jpg")
                                .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/quiz/NONE/car.jpg")
                                .build()
                        )
                        .member(DUMMY_ADMIN)
                        .isDefault(true)
                        .build();

                ImageQuiz IMAGE_QUIZ_OBJECT1 = ImageQuiz.builder()
                        .quizCategory(OBJECT)
                        .level(BRONZE)
                        .title("cap")
                        .answer("모자")
                        .s3Info(S3Info.builder()
                                .folderName("quiz/OBJECT")
                                .fileName("cap.jpg")
                                .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/quiz/OBJECT/cap.jpg")
                                .build()
                        )
                        .member(DUMMY_MEMBER)
                        .isDefault(false)
                        .build();

                ImageQuiz IMAGE_QUIZ_OBJECT2 = ImageQuiz.builder()
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
                        .isDefault(true)
                        .build();

                ImageQuiz IMAGE_QUIZ_OBJECT3 = ImageQuiz.builder()
                        .quizCategory(OBJECT)
                        .level(GOLD)
                        .title("bag")
                        .answer("가방")
                        .s3Info(S3Info.builder()
                                .folderName("quiz/OBJECT")
                                .fileName("bag.jpg")
                                .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/quiz/OBJECT/bag.jpg")
                                .build()
                        )
                        .member(DUMMY_ADMIN)
                        .isDefault(true)
                        .build();

                ImageQuiz IMAGE_QUIZ_OBJECT4 = ImageQuiz.builder()
                        .quizCategory(OBJECT)
                        .level(PLATINUM)
                        .title("ball")
                        .answer("공")
                        .s3Info(S3Info.builder()
                                .folderName("quiz/OBJECT")
                                .fileName("ball.jpg")
                                .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/quiz/OBJECT/ball.jpg")
                                .build()
                        )
                        .member(DUMMY_ADMIN)
                        .isDefault(true)
                        .build();

                ImageQuiz IMAGE_QUIZ_OBJECT5 = ImageQuiz.builder()
                        .quizCategory(OBJECT)
                        .level(DIAMOND)
                        .title("pencil")
                        .answer("연필")
                        .s3Info(S3Info.builder()
                                .folderName("quiz/OBJECT")
                                .fileName("pencil.jpg")
                                .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/quiz/OBJECT/pencil.jpg")
                                .build()
                        )
                        .member(DUMMY_ADMIN)
                        .isDefault(true)
                        .build();

                ImageQuiz IMAGE_QUIZ_OBJECT6 = ImageQuiz.builder()
                        .quizCategory(OBJECT)
                        .level(PLATINUM)
                        .title("scissors")
                        .answer("가위")
                        .s3Info(S3Info.builder()
                                .folderName("quiz/OBJECT")
                                .fileName("scissors.jpg")
                                .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/quiz/OBJECT/scissors.jpg")
                                .build()
                        )
                        .member(DUMMY_ADMIN)
                        .isDefault(true)
                        .build();

                ImageQuiz IMAGE_QUIZ_PLANT1 = ImageQuiz.builder()
                        .quizCategory(PLANT)
                        .level(BRONZE)
                        .title("cactus")
                        .answer("선인장")
                        .s3Info(S3Info.builder()
                                .folderName("quiz/PLANT")
                                .fileName("cactus.jpg")
                                .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/quiz/PLANT/cactus.jpg")
                                .build()
                        )
                        .member(DUMMY_ADMIN)
                        .isDefault(true)
                        .build();

                ImageQuiz IMAGE_QUIZ_PLANT2 = ImageQuiz.builder()
                        .quizCategory(PLANT)
                        .level(SILVER)
                        .title("aloe")
                        .answer("알로에")
                        .s3Info(S3Info.builder()
                                .folderName("quiz/PLANT")
                                .fileName("aloe.png")
                                .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/quiz/PLANT/aloe.png")
                                .build()
                        )
                        .member(DUMMY_ADMIN)
                        .isDefault(true)
                        .build();

                ImageQuiz IMAGE_QUIZ_PLANT3 = ImageQuiz.builder()
                        .quizCategory(PLANT)
                        .level(GOLD)
                        .title("potato")
                        .answer("감자")
                        .s3Info(S3Info.builder()
                                .folderName("quiz/PLANT")
                                .fileName("potato.jpg")
                                .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/quiz/PLANT/potato.jpg")
                                .build()
                        )
                        .member(DUMMY_ADMIN)
                        .isDefault(true)
                        .build();

                ImageQuiz IMAGE_QUIZ_PLANT4 = ImageQuiz.builder()
                        .quizCategory(PLANT)
                        .level(PLATINUM)
                        .title("strawberry")
                        .answer("딸기")
                        .s3Info(S3Info.builder()
                                .folderName("quiz/PLANT")
                                .fileName("strawberry.jpg")
                                .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/quiz/PLANT/strawberry.jpg")
                                .build()
                        )
                        .member(DUMMY_ADMIN)
                        .isDefault(true)
                        .build();

                ImageQuiz IMAGE_QUIZ_PLANT5 = ImageQuiz.builder()
                        .quizCategory(PLANT)
                        .level(PLATINUM)
                        .title("sunflower")
                        .answer("해바라기")
                        .s3Info(S3Info.builder()
                                .folderName("quiz/PLANT")
                                .fileName("sunflower.png")
                                .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/quiz/PLANT/sunflower.png")
                                .build()
                        )
                        .member(DUMMY_ADMIN)
                        .isDefault(true)
                        .build();

                ImageQuiz IMAGE_QUIZ_PLANT6 = ImageQuiz.builder()
                        .quizCategory(PLANT)
                        .level(PLATINUM)
                        .title("sweetpotato.jpg")
                        .answer("고구마")
                        .s3Info(S3Info.builder()
                                .folderName("quiz/PLANT")
                                .fileName("sweetpotato.jpg")
                                .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/quiz/PLANT/sweetpotato.jpg")
                                .build()
                        )
                        .member(DUMMY_ADMIN)
                        .isDefault(true)
                        .build();

                ImageQuiz IMAGE_QUIZ_PLANT7 = ImageQuiz.builder()
                        .quizCategory(PLANT)
                        .level(PLATINUM)
                        .title("tree")
                        .answer("나무")
                        .s3Info(S3Info.builder()
                                .folderName("quiz/PLANT")
                                .fileName("tree.jpg")
                                .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/quiz/PLANT/tree.jpg")
                                .build()
                        )
                        .member(DUMMY_ADMIN)
                        .isDefault(true)
                        .build();

                imageQuizList.add(IMAGE_QUIZ_ANIMAL1);
                imageQuizList.add(IMAGE_QUIZ_ANIMAL2);
                imageQuizList.add(IMAGE_QUIZ_ANIMAL3);
                imageQuizList.add(IMAGE_QUIZ_ANIMAL4);
                imageQuizList.add(IMAGE_QUIZ_ANIMAL5);
                imageQuizList.add(IMAGE_QUIZ_ANIMAL6);
                imageQuizList.add(IMAGE_QUIZ_ANIMAL7);

                imageQuizList.add(IMAGE_QUIZ_FOOD1);
                imageQuizList.add(IMAGE_QUIZ_FOOD2);
                imageQuizList.add(IMAGE_QUIZ_FOOD3);
                imageQuizList.add(IMAGE_QUIZ_FOOD4);
                imageQuizList.add(IMAGE_QUIZ_FOOD5);

                imageQuizList.add(IMAGE_QUIZ_NONE1);
                imageQuizList.add(IMAGE_QUIZ_NONE2);
                imageQuizList.add(IMAGE_QUIZ_NONE3);
                imageQuizList.add(IMAGE_QUIZ_NONE4);
                imageQuizList.add(IMAGE_QUIZ_NONE5);
                imageQuizList.add(IMAGE_QUIZ_NONE6);

                imageQuizList.add(IMAGE_QUIZ_PLANT1);
                imageQuizList.add(IMAGE_QUIZ_PLANT2);
                imageQuizList.add(IMAGE_QUIZ_PLANT3);
                imageQuizList.add(IMAGE_QUIZ_PLANT4);
                imageQuizList.add(IMAGE_QUIZ_PLANT5);
                imageQuizList.add(IMAGE_QUIZ_PLANT6);
                imageQuizList.add(IMAGE_QUIZ_PLANT7);

                imageQuizList.add(IMAGE_QUIZ_OBJECT1);
                imageQuizList.add(IMAGE_QUIZ_OBJECT2);
                imageQuizList.add(IMAGE_QUIZ_OBJECT3);
                imageQuizList.add(IMAGE_QUIZ_OBJECT4);
                imageQuizList.add(IMAGE_QUIZ_OBJECT5);
                imageQuizList.add(IMAGE_QUIZ_OBJECT6);
            }

            imageQuizRepository.saveAll(imageQuizList);
        }
    }
}
