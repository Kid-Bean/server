package soongsil.kidbean.server.quizsolve.application.vo;

import java.util.List;
import java.util.Map;
import soongsil.kidbean.server.quizsolve.application.vo.type.MorphemeCheckEnum;
import soongsil.kidbean.server.summary.domain.type.AgeGroup;

public record MorphemeCheckListResponse(
        List<MorphemeCheckListInfo> checkList
) {
    public record MorphemeCheckListInfo(
            String checkInfoContent,
            AgeGroup ageGroup,
            boolean isUsed
    ) {

    }

    public static MorphemeCheckListResponse from(Map<MorphemeCheckEnum, Boolean> checkList) {
        List<MorphemeCheckListInfo> morphemeCheckListInfoList = checkList.entrySet().stream()
                .map(entry -> new MorphemeCheckListInfo(
                        entry.getKey().getContent(),
                        entry.getKey().getAgeGroup(),
                        entry.getValue()
                ))
                .toList();

        return new MorphemeCheckListResponse(morphemeCheckListInfoList);
    }
}
