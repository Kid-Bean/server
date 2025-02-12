package soongsil.kidbean.server.answerquiz.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import soongsil.kidbean.server.answerquiz.repository.AnswerQuizRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class AnswerQuizCountCache {

    private final AnswerQuizRepository answerQuizRepository;

    private static final Map<Long, Long> answerQuizCountCache = new ConcurrentHashMap<>();

    public void plusCount(Long memberId) {
        if (answerQuizCountCache.containsKey(memberId)) {
            answerQuizCountCache.put(memberId, answerQuizCountCache.get(memberId) + 1);
        }
    }

    public void minusCount(Long memberId) {
        if (answerQuizCountCache.containsKey(memberId)) {
            answerQuizCountCache.put(memberId, answerQuizCountCache.get(memberId) - 1);
        }
    }

    public Long get(Long memberId) {
        // 캐시에서 값을 가져옴
        Long count = answerQuizCountCache.get(memberId);

        // 캐시에 값이 없으면 AnswerQuizRepository에서 값을 가져와 캐시에 넣음
        if (count == null) {
            count = answerQuizRepository.countByMemberId(memberId);
            if (count != null) {
                answerQuizCountCache.put(memberId, count);
            }
        }

        return count;
    }
}