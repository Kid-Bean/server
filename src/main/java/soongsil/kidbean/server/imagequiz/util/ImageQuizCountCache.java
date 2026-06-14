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
        // compute는 ConcurrentHashMap에서 원자적으로 동작하여 read-modify-write 경합을 방지한다.
        // 캐시에 값이 없으면 DB 조회값(이미 증가가 반영된 값)으로 초기화한다.
        imageQuizCountCache.compute(memberId, (id, count) ->
                count == null ? imageQuizRepository.countByMemberIdAndDefaultProblem(id) : count + 1);
    }

    public void minusCount(Long memberId) {
        imageQuizCountCache.compute(memberId, (id, count) ->
                count == null ? imageQuizRepository.countByMemberIdAndDefaultProblem(id) : count - 1);
    }

    public Long get(Long memberId) {
        return imageQuizCountCache.computeIfAbsent(memberId,
                imageQuizRepository::countByMemberIdAndDefaultProblem);
    }
}
