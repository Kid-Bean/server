package soongsil.kidbean.server.imagequiz.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.quizsolve.util.LockAndUnlock;

@Slf4j
@RequiredArgsConstructor
@Component
public class ImageQuizServiceFacade {

    private final MemberScoreUpdateStrategy memberScoreUpdateStrategy;

    @LockAndUnlock(lockName = "MEMBER_LOCK")
    @Transactional
    public void updateUserScore(Long score, Long memberId) {
        if (score != 0) {
            memberScoreUpdateStrategy.updateUserScore(memberId, score);
        }
    }
}
