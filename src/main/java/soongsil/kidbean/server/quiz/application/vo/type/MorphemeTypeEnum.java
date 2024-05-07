package soongsil.kidbean.server.quiz.application.vo.type;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum MorphemeTypeEnum {

    NOUN("명사", Arrays.asList("NNP", "NNG", "NNB")),
    EF("종결어미", Arrays.asList("EF")),
    VV("동사", Arrays.asList("VV")),
    VA("형용사", Arrays.asList("VA")),
    JKS("주격조사", Arrays.asList("JKS")),
    NP("대명사", Arrays.asList("NP")),
    NNB("의존 명사", Arrays.asList("NNB")),
    EC("연결 어미", Arrays.asList("EC")),
    MA("부사", Arrays.asList("MAG", "MAJ")),
    JX_JC("보조사", Arrays.asList("JX", "JC")),
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
