package soongsil.kidbean.server.imagequiz.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.quizsolve.util.LockAndUnlock;

@Slf4j
@RequiredArgsConstructor
@Component
public class ImageQuizServiceFacade {

    private final MemberRepository memberRepository;
    private final MemberScoreUpdateStrategy memberScoreUpdateStrategy;

    @LockAndUnlock(lockName = "MEMBER_LOCK")
    @Transactional
    public void updateUserScore(Long score, Long memberId) {
        if (score != 0) {
            try {
                memberRepository.getLock(memberId.toString());
                memberScoreUpdateStrategy.updateUserScore(memberId, score);
            } finally {
                memberRepository.releaseLock(memberId.toString());
            }
        }
    }
}
