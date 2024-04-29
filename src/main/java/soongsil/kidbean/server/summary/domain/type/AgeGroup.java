package soongsil.kidbean.server.summary.domain.type;

import java.time.LocalDate;
import java.time.Period;
import lombok.Getter;

@Getter
public enum AgeGroup {
    BEFORE_ONE,
    ONE,
    TWO,
    THREE,
    FOUR,
    AFTER_FIVE;

    public static AgeGroup calculate(LocalDate birthDate) {
        int age = Period.between(birthDate, LocalDate.now()).getYears();

        if (age < 1) {
            return AgeGroup.BEFORE_ONE;
        } else if (age == 1) {
            return AgeGroup.ONE;
        } else if (age == 2) {
            return AgeGroup.TWO;
        } else if (age == 3) {
            return AgeGroup.THREE;
        } else if (age == 4) {
            return AgeGroup.FOUR;
        } else {
            return AgeGroup.AFTER_FIVE;
        }
    }
}
