package soongsil.kidbean.server.quiz.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 원하는 개수의 난수를 생성
 */
public class RandNumUtil {

    /**
     * 난수 List 생성 메서드
     */
    public static List<Long> generateRandomNumbers(int startNum, int endNum, int size) {
        if (size > (endNum - startNum + 1)) {
            throw new IllegalArgumentException("요청된 크기가 가능한 난수 범위를 초과합니다.");
        }

        Set<Long> randNumSet = new HashSet<>();

        // 서로 다른 난수 들이 size 개수 만큼 될 때까지 반복
        while (randNumSet.size() < size) {
            long randNum = generateRandomNum(startNum, endNum);
            randNumSet.add(randNum);
        }

        // Set 을 List 로 변환 후 반환
        return new ArrayList<>(randNumSet);
    }

    private static int generateRandomNum(int startNum, int endNum) {
        return ThreadLocalRandom.current().nextInt(startNum, endNum + 1);
    }
}
