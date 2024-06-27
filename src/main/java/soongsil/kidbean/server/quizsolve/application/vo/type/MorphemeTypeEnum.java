package soongsil.kidbean.server.quizsolve.application.vo.type;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum MorphemeTypeEnum {

    NOUN("명사", List.of("NNP", "NNG", "NNB")),
    EF("종결어미", List.of("EF")),
    VV("동사", List.of("VV")),
    VA("형용사", List.of("VA")),
    JKS("주격조사", List.of("JKS")),
    NP("대명사", List.of("NP")),
    NNB("의존 명사", List.of("NNB")),
    EC("연결 어미", List.of("EC")),
    MA("부사", List.of("MAG", "MAJ")),
    JX_JC("보조사", List.of("JX", "JC")),
    EMPTY("기타", Collections.EMPTY_LIST),
    ;

    private final String title;
    private final List<String> typeList;

    MorphemeTypeEnum(String title, List<String> typeList) {
        this.title = title;
        this.typeList = typeList;
    }

    public static MorphemeTypeEnum findByMorpheme(String code) {
        return Arrays.stream(MorphemeTypeEnum.values())
                .filter(type -> type.hasType(code))
                .findAny()
                .orElse(EMPTY);
    }

    private boolean hasType(String code) {
        return typeList.contains(code);
    }
}
