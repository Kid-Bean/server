package soongsil.kidbean.server.member.application.type;

public enum HomeImageUrl {
    LEVEL1("https://kidbean.s3.ap-northeast-2.amazonaws.com/home/tree1.png", 100L),
    LEVEL2("https://kidbean.s3.ap-northeast-2.amazonaws.com/home/tree2.png", 200L),
    LEVEL3("https://kidbean.s3.ap-northeast-2.amazonaws.com/home/tree3.png", 300L),
    LEVEL4("https://kidbean.s3.ap-northeast-2.amazonaws.com/home/tree4.png", 400L),
    LEVEL5("https://kidbean.s3.ap-northeast-2.amazonaws.com/home/tree5.png", Long.MAX_VALUE),
    ;

    private final String url;
    private final Long maxScore;

    HomeImageUrl(String url, Long maxScore) {
        this.url = url;
        this.maxScore = maxScore;
    }

    /**
     * 아이의 점수를 받아와 maxScore과 비교를 통해 범위에 해당하는 이미지 url return
     * default는 LEVEL1의 url return (null 포함)
     */
    public static String getUrlByScore(Long score) {
        for (HomeImageUrl imageUrl : HomeImageUrl.values()) {
            if (score <= imageUrl.maxScore) {
                return imageUrl.url;
            }
        }

        return LEVEL1.url;
    }
}
