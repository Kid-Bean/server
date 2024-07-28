package soongsil.kidbean.server.quizsolve.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import soongsil.kidbean.server.member.repository.MemberRepository;

@Order(1)
@Aspect
@Slf4j
@RequiredArgsConstructor
@Component
public class LockUnLockAspect {

    private final MemberRepository memberRepository;

    // Lock 관리 로직
    private void acquireLock(String lockName, Long memberId) {
        String lockKey = lockName + memberId;

        memberRepository.getLock(lockKey);
    }

    private void releaseLock(String lockName, Long memberId) {
        String lockKey = lockName + memberId;

        memberRepository.releaseLock(lockKey);
    }

    @Before("@annotation(soongsil.kidbean.server.quizsolve.util.LockAndUnlock)")
    public void beforeTransaction(JoinPoint joinPoint) {
        LockAndUnlock lockAndUnlock = getLockAndUnlockAnnotation(joinPoint);
        if (lockAndUnlock != null) {
            acquireLock(lockAndUnlock.lockName(), getMemberIdFromArgs(joinPoint));
        }
    }

    @AfterReturning("@annotation(soongsil.kidbean.server.quizsolve.util.LockAndUnlock)")
    public void afterSuccessfulTransaction(JoinPoint joinPoint) {
        LockAndUnlock lockAndUnlock = getLockAndUnlockAnnotation(joinPoint);
        if (lockAndUnlock != null) {
            releaseLock(lockAndUnlock.lockName(), getMemberIdFromArgs(joinPoint));
        }
    }

    @AfterThrowing("@annotation(soongsil.kidbean.server.quizsolve.util.LockAndUnlock)")
    public void afterFailedTransaction(JoinPoint joinPoint) {
        LockAndUnlock lockAndUnlock = getLockAndUnlockAnnotation(joinPoint);
        if (lockAndUnlock != null) {
            releaseLock(lockAndUnlock.lockName(), getMemberIdFromArgs(joinPoint));
        }
    }

    private Long getMemberIdFromArgs(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = methodSignature.getParameterNames();

        for (int i = 0; i < parameterNames.length; i++) {
            if ("memberId".equals(parameterNames[i])) {
                return (Long) args[i];
            }
        }

        throw new IllegalArgumentException("memberId 파라미터를 찾을 수 없습니다.");
    }

    private LockAndUnlock getLockAndUnlockAnnotation(JoinPoint joinPoint) {
        return ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(LockAndUnlock.class);
    }
}
