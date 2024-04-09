package soongsil.kidbean.server.quiz.domain.type;

import lombok.Getter;

@Getter
public enum Level {

    DIAMOND(null), PLATINUM(DIAMOND), GOLD(PLATINUM), SILVER(GOLD), BRONZE(SILVER);

    private final Level next;

    Level(Level next) {
        this.next = next;
    }

    /**
     * level에 맞는 포인트를 reutrn
     *
     * @param level 문제의 Level
     * @return Long level에 맞는 점수를 return
     */
    public static Long getPoint(Level level) {
        return switch (level) {
            case DIAMOND -> 5L;
            case PLATINUM -> 4L;
            case GOLD -> 3L;
            case SILVER -> 2L;
            case BRONZE -> 1L;
        };
    }
}
