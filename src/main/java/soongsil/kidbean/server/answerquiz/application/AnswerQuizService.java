package soongsil.kidbean.server.answerquiz.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import soongsil.kidbean.server.answerquiz.domain.AnswerQuiz;
import soongsil.kidbean.server.answerquiz.dto.request.AnswerQuizSolvedRequest;
import soongsil.kidbean.server.answerquiz.dto.request.AnswerQuizUpdateRequest;
import soongsil.kidbean.server.answerquiz.dto.request.AnswerQuizUploadRequest;
import soongsil.kidbean.server.answerquiz.dto.response.*;
import soongsil.kidbean.server.answerquiz.exception.AnswerQuizNotFoundException;
import soongsil.kidbean.server.answerquiz.repository.AnswerQuizRepository;
import soongsil.kidbean.server.global.util.RandNumUtil;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.exception.MemberNotFoundException;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.quizsolve.application.AnswerQuizSolvedService;
import soongsil.kidbean.server.quizsolve.application.vo.OpenApiResponse;

import java.util.List;

import static soongsil.kidbean.server.answerquiz.exception.errorcode.AnswerQuizErrorCode.ANSWER_QUIZ_NOT_FOUND;
import static soongsil.kidbean.server.member.exception.errorcode.MemberErrorCode.MEMBER_NOT_FOUND;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AnswerQuizService {

    private final AnswerQuizRepository answerQuizRepository;
    private final MemberRepository memberRepository;
    private final OpenApiService openApiService;
    private final AnswerQuizSolvedService answerQuizSolvedService;

    private static final Long ANSWER_QUIZ_POINT = 3L;

    /**
     * 랜덤 문제를 생성 후 멤버에게 전달
     *
     * @param memberId 문제를 풀 멤버
     * @return 랜덤 문제가 들어 있는 DTO
     */
    public AnswerQuizListResponse selectRandomAnswerQuiz(Long memberId, Integer quizNum) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));

        int totalQuizNum = getAnswerQuizCount(member);

        List<AnswerQuizResponse> answerQuizResponseList =
                RandNumUtil.generateRandomNumbers(0, totalQuizNum - 1, quizNum).stream()
                        .map(quizIdx -> generateRandomPageWithCategory(member, quizIdx))
                        .map(AnswerQuizResponse::from)
                        .toList();

        return AnswerQuizListResponse.from(answerQuizResponseList);
    }

    /**
     * 대답하기 정답을 제출, 녹음 파일을 s3에 저장 후 응답 텍스트를 OpenApi로 분석
     *
     * @param answerQuizSolvedRequest 응답 텍스트 및 푼 문제 번호
     * @param multipartFile           녹음 파일
     * @param memberId                푼 사람
     * @return Long                   AnswerQuiz 풀었을 때의 점수
     */
    @Transactional
    public AnswerQuizSolveScoreResponse submitAnswerQuiz(AnswerQuizSolvedRequest answerQuizSolvedRequest,
                                                         MultipartFile multipartFile,
                                                         Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
        AnswerQuiz answerQuiz = answerQuizRepository.findById(answerQuizSolvedRequest.quizId())
                .orElseThrow(() -> new AnswerQuizNotFoundException(ANSWER_QUIZ_NOT_FOUND));

        String submitAnswer = answerQuizSolvedRequest.answer();
        OpenApiResponse openApiResponse = openApiService.analyzeAnswer(submitAnswer);

        answerQuizSolvedService.enrollNewAnswerQuizSolved(
                answerQuiz, submitAnswer, member, openApiResponse, multipartFile);

        return AnswerQuizSolveScoreResponse.scoreFrom(ANSWER_QUIZ_POINT);
    }

    private AnswerQuiz generateRandomPageWithCategory(Member member, int quizIdx) {
        return answerQuizRepository.findSingleResultByMember(member, PageRequest.of(quizIdx, 1)).get(0);
    }

    /**
     * 해당 멤버 또는 role이 어드민으로 등록된 사람이 등록한 answerQuiz 수 return
     *
     * @param member 문제를 풀고자 하는 멤버
     * @return answerQuiz 수
     */
    private Integer getAnswerQuizCount(Member member) {
        return answerQuizRepository.countByMemberOrAdmin(member);
    }

    public AnswerQuizMemberDetailResponse getAnswerQuizById(Long memberId, Long quizId) {
        AnswerQuiz answerQuiz = answerQuizRepository.findByQuizIdAndMember_MemberId(quizId, memberId)
                .orElseThrow(() -> new AnswerQuizNotFoundException(ANSWER_QUIZ_NOT_FOUND));

        return AnswerQuizMemberDetailResponse.from(answerQuiz);
    }

    public List<AnswerQuizMemberResponse> getAllAnswerQuizByMember(Long memberId) {
        return answerQuizRepository.findAllByMember_MemberId(memberId).stream()
                .map(AnswerQuizMemberResponse::from)
                .toList();
    }

    @Transactional
    public void uploadAnswerQuiz(AnswerQuizUploadRequest request, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));

        AnswerQuiz answerQuiz = request.toAnswerQuiz(member);

        answerQuizRepository.save(answerQuiz);
    }

    @Transactional
    public void updateAnswerQuiz(AnswerQuizUpdateRequest request, Long quizId) {
        AnswerQuiz answerQuiz = answerQuizRepository.findById(quizId)
                .orElseThrow(() -> new AnswerQuizNotFoundException(ANSWER_QUIZ_NOT_FOUND));

        answerQuiz.updateAnswerQuiz(request.title(), request.question());
    }

    @Transactional
    public void deleteAnswerQuiz(Long quizId) {
        AnswerQuiz answerQuiz = answerQuizRepository.findById(quizId)
                .orElseThrow(() -> new AnswerQuizNotFoundException(ANSWER_QUIZ_NOT_FOUND));

        answerQuizRepository.delete(answerQuiz);
    }
}
