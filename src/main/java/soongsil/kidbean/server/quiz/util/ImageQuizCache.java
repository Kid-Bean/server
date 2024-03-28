package soongsil.kidbean.server.quiz.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import soongsil.kidbean.server.quiz.domain.type.Category;

public class ImageQuizCache {

    //{category : {userID : problemCount}} -> 해당 유저가 해당 카테고리에서 가진 총 문제 수
    public static final Map<Category, Map<Long, Integer>> imageQuizUserProblemCountMap;

    static {
        imageQuizUserProblemCountMap = new ConcurrentHashMap<>();

        for (Category category : Category.values()) {
            imageQuizUserProblemCountMap.put(category, new ConcurrentHashMap<>());
        }
    }
}
