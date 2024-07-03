package soongsil.kidbean.server.imagequiz.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import soongsil.kidbean.server.imagequiz.repository.ImageQuizRepository;

@RequiredArgsConstructor
@Component
public class ImageQuizCountCache {

    private final ImageQuizRepository imageQuizRepository;

    private static final Map<Long, Long> imageQuizCountCache = new ConcurrentHashMap<>();

    public void plusCount(Long memberId) {
        if (imageQuizCountCache.containsKey(memberId)) {
            imageQuizCountCache.put(memberId, imageQuizCountCache.get(memberId) + 1);
        } else {    //plusCount 메서드 호출 전 이미 문제 수 증가
            imageQuizCountCache.put(memberId, imageQuizRepository.countByMemberIdAndDefaultProblem(memberId));
        }
    }

    public void minusCount(Long memberId) {
        if (imageQuizCountCache.containsKey(memberId)) {
            imageQuizCountCache.put(memberId, imageQuizCountCache.get(memberId) - 1);
        } else {    //minusCount 메서드 호출 전 이미 문제 수 감소
            imageQuizCountCache.put(memberId, imageQuizRepository.countByMemberIdAndDefaultProblem(memberId));
        }
    }

    public Long get(Long memberId) {
        // 캐시에서 값을 가져옴
        Long count = imageQuizCountCache.get(memberId);

        // 캐시에 값이 없으면 ImageQuizRepository에서 값을 가져와 캐시에 넣음
        if (count == null) {
            count = imageQuizRepository.countByMemberIdAndDefaultProblem(memberId);
            if (count != null) {
                imageQuizCountCache.put(memberId, count);
            }
        }

        return count;
    }
}
