package soongsil.kidbean.server.member.dto.response;

import java.util.List;
import soongsil.kidbean.server.quiz.domain.UseWord;

public record UseWordList(
        List<UseWordInfo> wordInfoList
) {
    public static UseWordList from(List<UseWordInfo> useWordInfoList) {
        return new UseWordList(useWordInfoList);
    }
}
