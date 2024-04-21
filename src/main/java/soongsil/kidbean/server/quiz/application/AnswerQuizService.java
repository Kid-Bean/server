package soongsil.kidbean.server.quiz.application;

import ch.qos.logback.core.testUtil.RandomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.domain.type.Role;
import soongsil.kidbean.server.member.exception.MemberNotFoundException;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.quiz.application.vo.OpenApiResponse;
import soongsil.kidbean.server.quiz.domain.AnswerQuiz;
import soongsil.kidbean.server.quiz.dto.request.AnswerQuizSolvedRequest;
import soongsil.kidbean.server.quiz.dto.request.AnswerQuizUpdateRequest;
import soongsil.kidbean.server.quiz.dto.request.AnswerQuizUploadRequest;
import soongsil.kidbean.server.quiz.dto.response.AnswerQuizMemberDetailResponse;
import soongsil.kidbean.server.quiz.dto.response.AnswerQuizMemberResponse;
import soongsil.kidbean.server.quiz.dto.response.AnswerQuizResponse;
import soongsil.kidbean.server.quiz.dto.response.AnswerQuizSolveScoreResponse;
import soongsil.kidbean.server.quiz.exception.AnswerQuizNotFoundException;
import soongsil.kidbean.server.quiz.exception.WordQuizNotFoundException;
import soongsil.kidbean.server.quiz.repository.AnswerQuizRepository;

import java.util.List;
import java.util.Optional;

import static soongsil.kidbean.server.member.exception.errorcode.MemberErrorCode.MEMBER_NOT_FOUND;
import static soongsil.kidbean.server.quiz.exception.errorcode.QuizErrorCode.ANSWER_QUIZ_NOT_FOUND;
import static soongsil.kidbean.server.quiz.exception.errorcode.QuizErrorCode.WORD_QUIZ_NOT_FOUND;

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
    public AnswerQuizResponse selectRandomAnswerQuiz(Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
        Page<AnswerQuiz> answerQuizPage = generateRandomAnswerQuizPage(member);

        AnswerQuiz answerQuiz = pageHasAnswerQuiz(answerQuizPage)
                .orElseThrow(() -> new WordQuizNotFoundException(WORD_QUIZ_NOT_FOUND));

        return AnswerQuizResponse.from(answerQuiz);
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

    /**
     * 랜덤 AnswerQuiz 생성
     *
     * @param member 문제를 풀 멤버
     * @return 랜덤 AnswerQuiz 있는 Page
     */
    private Page<AnswerQuiz> generateRandomAnswerQuizPage(Member member) {

        int divVal = getAnswerQuizCount(member);
        int idx = RandomUtil.getPositiveInt() % divVal;

        return answerQuizRepository.findByMemberOrMember_Role(member, Role.ADMIN, PageRequest.of(idx, 1));
    }

    /**
     * 해당 멤버 또는 role이 어드민으로 등록된 사람이 등록한 answerQuiz 수 return
     *
     * @param member 문제를 풀고자 하는 멤버
     * @return answerQuiz 수
     */
    private Integer getAnswerQuizCount(Member member) {
        return answerQuizRepository.countByMemberOrMember_Role(member, Role.ADMIN);
    }

    /**
     * 해당 페이지에 AnswerQuiz 있는지 확인 후 Optional로 감싸 return
     *
     * @param answerQuizPage answerQuiz가 있는 Page
     * @return answerQuiz가 있는 Optional
     */
    private Optional<AnswerQuiz> pageHasAnswerQuiz(Page<AnswerQuiz> answerQuizPage) {
        if (answerQuizPage.hasContent()) {
            return Optional.of(answerQuizPage.getContent().get(0));
        } else {
            return Optional.empty();
        }
    }

    public AnswerQuizMemberDetailResponse getAnswerQuizById(Long memberId, Long quizId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));

        AnswerQuiz answerQuiz = answerQuizRepository.findByQuizIdAndMember(quizId, member)
                .orElseThrow(() -> new AnswerQuizNotFoundException(ANSWER_QUIZ_NOT_FOUND));

        return AnswerQuizMemberDetailResponse.from(answerQuiz);
    }

    public List<AnswerQuizMemberResponse> getAllAnswerQuizByMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));

        return answerQuizRepository.findAllByMember(member)
                .stream()
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
    public void updateAnswerQuiz(AnswerQuizUpdateRequest request, Long memberId, Long quizId) {
        AnswerQuiz answerQuiz = answerQuizRepository.findById(quizId)
                .orElseThrow(() -> new AnswerQuizNotFoundException(ANSWER_QUIZ_NOT_FOUND));

        answerQuiz.update(request.title(), request.question());
    }
}
