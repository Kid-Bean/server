package soongsil.kidbean.server.mypage.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.mypage.dto.response.SolvedImageDetailResponse;
import soongsil.kidbean.server.mypage.dto.response.SolvedImageInfo;
import soongsil.kidbean.server.mypage.dto.response.SolvedImageListResponse;
import soongsil.kidbean.server.mypage.dto.response.SolvedRecordInfo;
import soongsil.kidbean.server.mypage.dto.response.SolvedRecordListResponse;
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

    /**
     * @param memberId 멤버Id
     * @return SolvedImageListResponse 푼 문제 리스트
     */
    public SolvedImageListResponse findSolvedImage(Long memberId) {
        Member member = findMemberById(memberId);

        List<SolvedImageInfo> solvedImageInfoList = imageQuizSolvedRepository.findAllByMember(member).stream()
                .map(SolvedImageInfo::from)
                .toList();

        return SolvedImageListResponse.from(solvedImageInfoList);
    }

    /**
     * @param solvedId 푼 Id
     * @return 푼 문제 상세 정보
     */
    public SolvedImageDetailResponse solvedImageDetail(Long solvedId) {
        ImageQuizSolved imageQuizSolved = findImageQuizSolvedById(solvedId);

        return SolvedImageDetailResponse.from(imageQuizSolved);
    }

    /**
     * @param memberId 멤버 Id
     * @return 푼 문제 리스트
     */
    public SolvedRecordListResponse findSolvedSentence(Long memberId) {
        Member member = findMemberById(memberId);

        List<SolvedRecordInfo> solvedSentenceInfoList = recordQuizSolvedRepository.findAllByMemberAndSentenceQuizIsNotNull(member).stream()
                .map(SolvedRecordInfo::from)
                .toList();

        return SolvedRecordListResponse.from(solvedSentenceInfoList);
    }

    public SolvedRecordListResponse findSolvedVoice(Long memberId) {
        Member member = findMemberById(memberId);

        List<SolvedRecordInfo> solvedSentenceInfoList = recordQuizSolvedRepository.findAllByMemberAndAnswerQuizIsNotNull(member).stream()
                .map(SolvedRecordInfo::from)
                .toList();

        return SolvedRecordListResponse.from(solvedSentenceInfoList);
    }

    private ImageQuizSolved findImageQuizSolvedById(Long solvedId) {
        return imageQuizSolvedRepository.findById(solvedId)
                .orElseThrow(RuntimeException::new);
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(RuntimeException::new);
    }
}
