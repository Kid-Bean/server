package soongsil.kidbean.server.quiz.domain.type;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public enum QuizCategory {

    ANIMAL("동물", 0),
    PLANT("식물", 1),
    OBJECT("사물", 2),
    NONE("없음", 3);

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
}
