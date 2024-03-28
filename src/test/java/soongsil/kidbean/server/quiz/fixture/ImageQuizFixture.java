package soongsil.kidbean.server.quiz.fixture;

import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER;

import soongsil.kidbean.server.global.vo.ImageInfo;
import soongsil.kidbean.server.quiz.domain.ImageQuiz;
import soongsil.kidbean.server.quiz.domain.type.Category;

public class ImageQuizFixture {

    public static final ImageQuiz IMAGE_QUIZ_ANIMAL = ImageQuiz.builder()
            .category(Category.ANIMAL)
            .member(MEMBER)
            .answer("animalAnswer")
            .title("animalTitle")
            .imageInfo(new ImageInfo("animal", null, null))
            .build();

    public static final ImageQuiz IMAGE_QUIZ_ANIMAL2 = ImageQuiz.builder()
            .category(Category.ANIMAL)
            .member(MEMBER)
            .answer("animalAnswer2")
            .title("animalTitle2")
            .imageInfo(new ImageInfo("animal2", null, null))
            .build();

    public static final ImageQuiz IMAGE_QUIZ_PLANT = ImageQuiz.builder()
            .category(Category.PLANT)
            .member(MEMBER)
            .answer("plantAnswer")
            .title("planTitle")
            .imageInfo(new ImageInfo("plan", null, null))
            .build();

    public static final ImageQuiz IMAGE_QUIZ_OBJECT = ImageQuiz.builder()
            .category(Category.OBJECT)
            .member(MEMBER)
            .answer("objectAnswer")
            .title("objectTitle")
            .imageInfo(new ImageInfo("object", null, null))
            .build();

    public static final ImageQuiz IMAGE_QUIZ_NONE = ImageQuiz.builder()
            .category(Category.NONE)
            .member(MEMBER)
            .answer("noneAnswer")
            .title("noneTitle")
            .imageInfo(new ImageInfo("none", null, null))
            .build();
}
