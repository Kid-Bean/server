package soongsil.kidbean.server.quiz.domain.type;

import java.util.List;
import lombok.Getter;

@Getter
public enum QuizCategory {


    NONE("없음"),
    ANIMAL("동물"),
    PLANT("식물"),
    OBJECT("사물"),
    FOOD("음식"),
    ;

    private final String categoryName;

    QuizCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    public static List<QuizCategory> allValue() {
        return List.of(values());
    }
}
