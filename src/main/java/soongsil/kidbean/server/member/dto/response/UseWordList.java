package soongsil.kidbean.server.member.dto.response;

import java.util.List;

public record UseWordList(
        List<UseWordInfo> wordInfoList
) {
    public static UseWordList from(List<UseWordInfo> useWordInfoList) {
        return new UseWordList(useWordInfoList);
    }
}
