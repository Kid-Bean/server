package soongsil.kidbean.server.program.domain.type;

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

    Date(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
