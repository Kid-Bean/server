package soongsil.kidbean.server.imagequiz.application;

import static soongsil.kidbean.server.member.exception.errorcode.MemberErrorCode.MEMBER_NOT_FOUND;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.exception.MemberNotFoundException;
import soongsil.kidbean.server.member.repository.MemberRepository;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberScoreUpdateStrategy {

    private final MemberRepository memberRepository;

    @SneakyThrows(InterruptedException.class)
    @Transactional
    public void updateUserScore(Long score, Long memberId) {

        Member member = memberRepository.findByIdOptimisticLock(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));

        while (true) {
            try {
                member.updateScore(member.getScore() + score);
                break;
            } catch (ObjectOptimisticLockingFailureException e) {
                Thread.sleep(30);
            }
        }
    }
}
