package soongsil.kidbean.server.mypage.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.mypage.dto.response.SolvedImageInfo;
import soongsil.kidbean.server.mypage.dto.response.SolvedImageListResponse;
import soongsil.kidbean.server.quiz.domain.ImageQuizSolved;
import soongsil.kidbean.server.quiz.repository.ImageQuizSolvedRepository;
import soongsil.kidbean.server.quiz.repository.RecordQuizSolvedRepository;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuizSolvedService {

    private final ImageQuizSolvedRepository imageQuizSolvedRepository;
    private final RecordQuizSolvedRepository recordQuizSolvedRepository;
    private final MemberRepository memberRepository;

    public SolvedImageListResponse findSolvedImage(Long memberId) {
        Member member = findMemberById(memberId);

        List<SolvedImageInfo> solvedImageInfoList = imageQuizSolvedRepository.findAllByMember(member).stream()
                .map(SolvedImageInfo::from)
                .toList();

        return SolvedImageListResponse.of(solvedImageInfoList);
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(RuntimeException::new);
    }
}
