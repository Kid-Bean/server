package soongsil.kidbean.server.member.application;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.dto.response.SolvedImageListResponse;
import soongsil.kidbean.server.member.dto.response.SolvedRecordInfo;
import soongsil.kidbean.server.member.dto.response.SolvedRecordListResponse;
import soongsil.kidbean.server.member.dto.response.SolvedVoiceDetailResponse;
import soongsil.kidbean.server.member.dto.response.SolvedWordDetailResponse;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.member.dto.response.SolvedImageDetailResponse;
import soongsil.kidbean.server.member.dto.response.SolvedImageInfo;
import soongsil.kidbean.server.quiz.domain.Analysis;
import soongsil.kidbean.server.quiz.domain.ImageQuizSolved;
import soongsil.kidbean.server.quiz.domain.AnswerQuizSolved;
import soongsil.kidbean.server.quiz.repository.AnalysisRepository;
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
    private final AnalysisRepository analysisRepository;

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
    public SolvedRecordListResponse findSolvedWord(Long memberId) {
        Member member = findMemberById(memberId);

        List<SolvedRecordInfo> solvedWordInfoList = recordQuizSolvedRepository.findAllByMemberAndWordQuizIsNotNull(
                        member).stream()
                .map(SolvedRecordInfo::from)
                .toList();

        return SolvedRecordListResponse.from(solvedWordInfoList);
    }

    public SolvedWordDetailResponse solvedWordDetail(Long solvedId) {
        AnswerQuizSolved answerQuizSolved = findRecordQuizSolvedById(solvedId);
        Optional<Analysis> optionalAnalysis = analysisRepository.findByAnswerQuizSolved(answerQuizSolved);

        return optionalAnalysis.map(analysis -> SolvedWordDetailResponse.of(answerQuizSolved, analysis))
                .orElseGet(() -> SolvedWordDetailResponse.of(answerQuizSolved, null));
    }

    public SolvedRecordListResponse findSolvedVoice(Long memberId) {
        Member member = findMemberById(memberId);

        List<SolvedRecordInfo> solvedWordInfoList = recordQuizSolvedRepository.findAllByMemberAndAnswerQuizIsNotNull(
                        member).stream()
                .map(SolvedRecordInfo::from)
                .toList();

        return SolvedRecordListResponse.from(solvedWordInfoList);
    }

    public SolvedVoiceDetailResponse solvedVoiceDetail(Long solvedId) {
        AnswerQuizSolved answerQuizSolved = findRecordQuizSolvedById(solvedId);
        Optional<Analysis> optionalAnalysis = analysisRepository.findByAnswerQuizSolved(answerQuizSolved);

        return optionalAnalysis.map(analysis -> SolvedVoiceDetailResponse.of(answerQuizSolved, analysis))
                .orElseGet(() -> SolvedVoiceDetailResponse.of(answerQuizSolved, null));
    }

    private ImageQuizSolved findImageQuizSolvedById(Long solvedId) {
        return imageQuizSolvedRepository.findById(solvedId)
                .orElseThrow(RuntimeException::new);
    }

    private AnswerQuizSolved findRecordQuizSolvedById(Long recordId) {
        return recordQuizSolvedRepository.findById(recordId)
                .orElseThrow(RuntimeException::new);
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(RuntimeException::new);
    }
}
