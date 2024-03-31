package soongsil.kidbean.server.quiz.fixture;

import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER;
import static soongsil.kidbean.server.quiz.domain.type.Category.*;
import static soongsil.kidbean.server.quiz.domain.type.Level.BRONZE;
import static soongsil.kidbean.server.quiz.domain.type.Level.DIAMOND;
import static soongsil.kidbean.server.quiz.domain.type.Level.GOLD;
import static soongsil.kidbean.server.quiz.domain.type.Level.PLATINUM;
import static soongsil.kidbean.server.quiz.domain.type.Level.SILVER;

import soongsil.kidbean.server.global.vo.ImageInfo;
import soongsil.kidbean.server.quiz.domain.ImageQuiz;

public class ImageQuizFixture {

    public static final ImageQuiz IMAGE_QUIZ_ANIMAL = ImageQuiz.builder()
            .quizId(1L)
            .category(ANIMAL)
            .member(MEMBER)
            .level(BRONZE)
            .answer("animalAnswer")
            .title("animalTitle")
            .imageInfo(new ImageInfo("animal", null, null))
            .build();

    public static final ImageQuiz IMAGE_QUIZ_ANIMAL2 = ImageQuiz.builder()
            .quizId(2L)
            .category(ANIMAL)
            .member(MEMBER)
            .level(SILVER)
            .answer("animalAnswer2")
            .title("animalTitle2")
            .imageInfo(new ImageInfo("animal2", null, null))
            .build();

    public static final ImageQuiz IMAGE_QUIZ_PLANT = ImageQuiz.builder()
            .quizId(3L)
            .category(PLANT)
            .member(MEMBER)
            .level(GOLD)
            .answer("plantAnswer")
            .title("planTitle")
            .imageInfo(new ImageInfo("plan", null, null))
            .build();

    public static final ImageQuiz IMAGE_QUIZ_OBJECT = ImageQuiz.builder()
            .quizId(4L)
            .category(OBJECT)
            .member(MEMBER)
            .level(PLATINUM)
            .answer("objectAnswer")
            .title("objectTitle")
            .imageInfo(new ImageInfo("object", null, null))
            .build();

    public static final ImageQuiz IMAGE_QUIZ_NONE = ImageQuiz.builder()
            .quizId(5L)
            .category(NONE)
            .member(MEMBER)
            .level(DIAMOND)
            .answer("noneAnswer")
            .title("noneTitle")
            .imageInfo(new ImageInfo("none", null, null))
            .build();
}
