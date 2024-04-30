package soongsil.kidbean.server.quiz.fixture;

import static soongsil.kidbean.server.member.fixture.MemberFixture.ADMIN;
import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER1;
import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER2;
import static soongsil.kidbean.server.quiz.domain.type.QuizCategory.*;
import static soongsil.kidbean.server.quiz.domain.type.Level.BRONZE;
import static soongsil.kidbean.server.quiz.domain.type.Level.DIAMOND;
import static soongsil.kidbean.server.quiz.domain.type.Level.GOLD;
import static soongsil.kidbean.server.quiz.domain.type.Level.PLATINUM;
import static soongsil.kidbean.server.quiz.domain.type.Level.SILVER;

import org.springframework.test.util.ReflectionTestUtils;
import soongsil.kidbean.server.global.vo.S3Info;
import soongsil.kidbean.server.quiz.domain.ImageQuiz;

public class ImageQuizFixture {

    public static final ImageQuiz IMAGE_QUIZ_ANIMAL1 = ImageQuiz.builder()
            .quizCategory(ANIMAL)
            .member(MEMBER1)
            .level(BRONZE)
            .answer("animalAnswer")
            .title("animalTitle")
            .s3Info(new S3Info("animal", null, null))
            .build();

    public static final ImageQuiz IMAGE_QUIZ_ANIMAL2 = ImageQuiz.builder()
            .quizCategory(ANIMAL)
            .member(MEMBER1)
            .level(SILVER)
            .answer("animalAnswer2")
            .title("animalTitle2")
            .s3Info(new S3Info("animal2", null, null))
            .build();

    public static final ImageQuiz IMAGE_QUIZ_PLANT = ImageQuiz.builder()
            .quizCategory(PLANT)
            .member(MEMBER2)
            .level(GOLD)
            .answer("plantAnswer")
            .title("planTitle")
            .s3Info(new S3Info("plan", null, null))
            .build();

    public static final ImageQuiz IMAGE_QUIZ_OBJECT = ImageQuiz.builder()
            .quizCategory(OBJECT)
            .member(MEMBER2)
            .level(PLATINUM)
            .answer("objectAnswer")
            .title("objectTitle")
            .s3Info(new S3Info("object", null, null))
            .build();

    public static final ImageQuiz IMAGE_QUIZ_NONE = ImageQuiz.builder()
            .quizCategory(NONE)
            .member(ADMIN)
            .level(DIAMOND)
            .answer("noneAnswer")
            .title("noneTitle")
            .s3Info(new S3Info("none", null, null))
            .build();

    static {
        ReflectionTestUtils.setField(IMAGE_QUIZ_ANIMAL1, "quizId", 1L);
        ReflectionTestUtils.setField(IMAGE_QUIZ_ANIMAL2, "quizId", 2L);
        ReflectionTestUtils.setField(IMAGE_QUIZ_PLANT, "quizId", 3L);
        ReflectionTestUtils.setField(IMAGE_QUIZ_OBJECT, "quizId", 4L);
        ReflectionTestUtils.setField(IMAGE_QUIZ_NONE, "quizId", 5L);
    }
}
