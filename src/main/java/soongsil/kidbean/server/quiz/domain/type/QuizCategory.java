package soongsil.kidbean.server.quiz.domain.type;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public enum QuizCategory {


    NONE("없음", 0),
    ANIMAL("동물", 1),
    PLANT("식물", 2),
    OBJECT("사물", 3),
    FOOD("음식", 4)
    ;

    private final String categoryName;
    private final int categoryCode;

    QuizCategory(String categoryName, int categoryCode) {
        this.categoryName = categoryName;
        this.categoryCode = categoryCode;
    }

    //categoryCode와 이에 해당하는 Category의 Map
    private static final Map<Integer, QuizCategory> BY_CODE = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(QuizCategory::getCategoryCode, Function.identity())));

    public static QuizCategory valueOfCode(int categoryCode) {
        return BY_CODE.get(categoryCode);
    }

    public static List<QuizCategory> allValue() {
        return List.of(values());
    }
}
