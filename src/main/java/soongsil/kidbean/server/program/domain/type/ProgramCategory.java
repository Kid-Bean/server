package soongsil.kidbean.server.program.domain.type;

import lombok.Getter;

@Getter
public enum ProgramCategory {

    HOSPITAL("병원"),
    ACADEMY("학습");

    private final String programCategory;

    ProgramCategory(String programCategory) {
        this.programCategory = programCategory;
    }

}
