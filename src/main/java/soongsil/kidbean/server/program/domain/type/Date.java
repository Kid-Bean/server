package soongsil.kidbean.server.program.domain.type;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public enum Date {
    MONDAY("월"),
    TUESDAY("화"),
    WEDNESDAY("수"),
    THURSDAY("목"),
    FRIDAY("금"),
    SATURDAY("토"),
    SUNDAY("일");

    private final String dayOfWeek;

    private static final Map<String, Date> BY_DAY_OF_WEEK = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(Date::getDayOfWeek, Function.identity())));

    public static Date getDayOfWeek(String dayOfWeek) {
        return BY_DAY_OF_WEEK.get(dayOfWeek);
    }

    Date(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
