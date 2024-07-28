package soongsil.kidbean.server.imagequiz.application;

import static soongsil.kidbean.server.member.exception.errorcode.MemberErrorCode.MEMBER_NOT_FOUND;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.exception.MemberNotFoundException;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.quizsolve.util.LockAndUnlock;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberScoreUpdateStrategy {

    private final MemberRepository memberRepository;

    @LockAndUnlock(lockName = "MEMBER_LOCK")
    @Transactional
    public void updateUserScore(Long memberId, Long score) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));

        log.info("Member {}'s score is updated. Current score: {}, Added score: {}", member.getMemberId(),
                member.getScore(), score);

        member.updateScore(member.getScore() + score);
    }
}
