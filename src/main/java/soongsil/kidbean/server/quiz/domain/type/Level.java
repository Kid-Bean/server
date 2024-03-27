package soongsil.kidbean.server.quiz.domain.type;

import lombok.Getter;

@Getter
public enum Level {

    DIAMOND(null), PLATINUM(DIAMOND), GOLD(PLATINUM), SILVER(GOLD), BRONZE(SILVER);

    private final Level next;

    Level(Level next) {
        this.next = next;
    }
}
