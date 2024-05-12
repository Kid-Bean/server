package soongsil.kidbean.server.summary.application;

import static soongsil.kidbean.server.member.exception.errorcode.MemberErrorCode.MEMBER_NOT_FOUND;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.dto.response.AgeScoreInfo;
import soongsil.kidbean.server.member.dto.response.ImageQuizScoreResponse;
import soongsil.kidbean.server.member.dto.response.MyScoreInfo;
import soongsil.kidbean.server.member.exception.MemberNotFoundException;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.summary.domain.type.AgeGroup;
import soongsil.kidbean.server.summary.repository.AverageScoreRepository;
import soongsil.kidbean.server.summary.repository.QuizScoreRepository;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SummaryService {

    private final QuizScoreRepository imageQuizScoreRepository;
    private final AverageScoreRepository averageScoreRepository;
    private final MemberRepository memberRepository;

    public ImageQuizScoreResponse findImageQuizScore(Long memberId) {
        Member member = findMemberById(memberId);

        List<MyScoreInfo> myScoreInfo = imageQuizScoreRepository.findAllByMember(member)
                .stream().map(MyScoreInfo::from)
                .toList();

        if (member.getBirthDate() != null) {
            AgeGroup memberAgeGroup = AgeGroup.calculate(member.getBirthDate());
            List<AgeScoreInfo> ageScoreInfo = averageScoreRepository.findAllByAgeGroup(memberAgeGroup).stream()
                    .map(AgeScoreInfo::from)
                    .toList();

            return ImageQuizScoreResponse.of(myScoreInfo, ageScoreInfo);
        }

        return ImageQuizScoreResponse.of(myScoreInfo, List.of());
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
    }
}
