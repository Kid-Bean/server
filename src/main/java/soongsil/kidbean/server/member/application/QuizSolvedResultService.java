package soongsil.kidbean.server.member.application;

import static soongsil.kidbean.server.member.exception.errorcode.MemberErrorCode.*;
import static soongsil.kidbean.server.quizsolve.exception.errorcode.QuizSolvedErrorCode.QUIZ_SOLVED_NOT_FOUND;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.dto.response.SolvedImageListResponse;
import soongsil.kidbean.server.member.dto.response.SolvedWordDetailResponse;
import soongsil.kidbean.server.member.dto.response.SolvedWordQuizInfo;
import soongsil.kidbean.server.member.dto.response.SolvedWordQuizListResponse;
import soongsil.kidbean.server.member.exception.MemberNotFoundException;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.member.dto.response.SolvedImageDetailResponse;
import soongsil.kidbean.server.member.dto.response.SolvedImageInfo;
import soongsil.kidbean.server.quizsolve.domain.QuizSolved;
import soongsil.kidbean.server.wordquiz.domain.Word;
import soongsil.kidbean.server.quizsolve.exception.QuizSolvedNotFoundException;
import soongsil.kidbean.server.quizsolve.repository.QuizSolvedRepository;
import soongsil.kidbean.server.wordquiz.repository.WordRepository;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuizSolvedResultService {

    private final QuizSolvedRepository quizSolvedRepository;
    private final WordRepository wordRepository;
    private final MemberRepository memberRepository;

    /**
     * @param memberId 멤버Id
     * @return SolvedImageListResponse 푼 문제 리스트
     */
    public SolvedImageListResponse findSolvedImage(Long memberId, boolean isCorrect) {
        Member member = findMemberById(memberId);

        List<SolvedImageInfo> solvedImageInfoList = quizSolvedRepository.findAllByMemberAndIsCorrectAndImageQuizIsNotNull(
                        member, isCorrect).stream()
                .map(SolvedImageInfo::from)
                .toList();

        return SolvedImageListResponse.from(solvedImageInfoList);
    }

    /**
     * @param solvedId 푼 Id
     * @return 푼 문제 상세 정보
     */
    public SolvedImageDetailResponse solvedImageDetail(Long solvedId) {
        QuizSolved imageQuizSolved = findQuizSolvedById(solvedId);

        return SolvedImageDetailResponse.from(imageQuizSolved);
    }

    /**
     * 푼 단어 관련 문제 리스트 return
     *
     * @param memberId 멤버 Id
     * @return 푼 문제 리스트
     */
    public SolvedWordQuizListResponse findSolvedWord(Long memberId) {
        Member member = findMemberById(memberId);

        List<SolvedWordQuizInfo> solvedWordQuizInfoList = quizSolvedRepository.findAllByMemberAndWordQuizNotNull(member)
                .stream()
                .map(SolvedWordQuizInfo::from)
                .toList();

        return SolvedWordQuizListResponse.from(solvedWordQuizInfoList);
    }

    /**
     * 푼 단어 관련 문제 디테일
     *
     * @param solvedId 푼 id
     */
    public SolvedWordDetailResponse solvedWordDetail(Long solvedId) {
        QuizSolved quizSolved = findQuizSolvedById(solvedId);
        List<Word> wordList = wordRepository.findAllByWordQuiz(quizSolved.getWordQuiz());

        return SolvedWordDetailResponse.from(quizSolved, wordList);
    }

    private QuizSolved findQuizSolvedById(Long solvedId) {
        return quizSolvedRepository.findById(solvedId)
                .orElseThrow(() -> new QuizSolvedNotFoundException(QUIZ_SOLVED_NOT_FOUND));
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
    }
}
