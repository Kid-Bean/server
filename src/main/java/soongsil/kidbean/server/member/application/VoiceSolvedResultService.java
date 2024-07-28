package soongsil.kidbean.server.member.application;

import static soongsil.kidbean.server.member.exception.errorcode.MemberErrorCode.MEMBER_NOT_FOUND;
import static soongsil.kidbean.server.quizsolve.exception.errorcode.QuizSolvedErrorCode.ANSWER_QUIZ_SOLVED_NOT_FOUND;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.dto.response.SolvedAnswerDetailResponse;
import soongsil.kidbean.server.member.dto.response.SolvedAnswerQuizInfo;
import soongsil.kidbean.server.member.dto.response.SolvedAnswerQuizListResponse;
import soongsil.kidbean.server.member.dto.response.UseWordInfo;
import soongsil.kidbean.server.member.dto.response.UseWordList;
import soongsil.kidbean.server.member.exception.MemberNotFoundException;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.quizsolve.application.AnalyzeMorphemeService;
import soongsil.kidbean.server.answerquiz.application.OpenApiService;
import soongsil.kidbean.server.quizsolve.application.vo.MorphemeCheckListResponse;
import soongsil.kidbean.server.quizsolve.application.vo.OpenApiResponse;
import soongsil.kidbean.server.quizsolve.domain.AnswerQuizSolved;
import soongsil.kidbean.server.answerquiz.exception.AnswerQuizNotFoundException;
import soongsil.kidbean.server.quizsolve.repository.AnswerQuizSolvedRepository;
import soongsil.kidbean.server.quizsolve.repository.UseWordRepository;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VoiceSolvedResultService {

    private final MemberRepository memberRepository;
    private final UseWordRepository useWordRepository;
    private final AnswerQuizSolvedRepository answerQuizSolvedRepository;
    private final AnalyzeMorphemeService analyzeMorphemeService;
    private final OpenApiService openApiService;

    public UseWordList findTop5UseWordList(Long memberId) {
        Member member = findMemberById(memberId);

        return UseWordList.from(find5SortedWordInfo(member));
    }

    public SolvedAnswerQuizListResponse findSolvedAnswerQuiz(Long memberId) {
        Member member = findMemberById(memberId);

        List<SolvedAnswerQuizInfo> solvedWordInfoList = answerQuizSolvedRepository.findAllByMember(member).stream()
                .map(SolvedAnswerQuizInfo::from)
                .toList();

        return SolvedAnswerQuizListResponse.from(solvedWordInfoList);
    }

    public SolvedAnswerDetailResponse solvedAnswerQuizDetail(Long solvedId) {
        AnswerQuizSolved answerQuizSolved = findAnswerQuizSolvedById(solvedId);
        OpenApiResponse openApiResponse = openApiService.analyzeAnswer(answerQuizSolved.getSentenceAnswer());

        MorphemeCheckListResponse checkListResponse = analyzeMorphemeService.createMorphemeCheckList(
                openApiResponse.morphemeVOList());

        return SolvedAnswerDetailResponse.of(answerQuizSolved, checkListResponse);
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
    }

    private List<UseWordInfo> find5SortedWordInfo(Member member) {
        return useWordRepository.findTop5ByMemberOrderByCountDesc(member).stream()
                .map(UseWordInfo::from)
                .toList();
    }

    private AnswerQuizSolved findAnswerQuizSolvedById(Long recordId) {
        return answerQuizSolvedRepository.findById(recordId)
                .orElseThrow(() -> new AnswerQuizNotFoundException(ANSWER_QUIZ_SOLVED_NOT_FOUND));
    }
}
