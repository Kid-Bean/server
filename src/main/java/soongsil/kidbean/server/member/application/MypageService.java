package soongsil.kidbean.server.member.application;

import static soongsil.kidbean.server.member.exception.errorcode.MemberErrorCode.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.dto.response.ImageQuizScoreResponse;
import soongsil.kidbean.server.member.dto.response.AgeScoreInfo;
import soongsil.kidbean.server.member.dto.response.MyScoreInfo;
import soongsil.kidbean.server.member.dto.response.SolvedAnswerDetailResponse;
import soongsil.kidbean.server.member.dto.response.SolvedImageListResponse;
import soongsil.kidbean.server.member.dto.response.SolvedAnswerQuizListResponse;
import soongsil.kidbean.server.member.dto.response.SolvedWordDetailResponse;
import soongsil.kidbean.server.member.dto.response.SolvedWordQuizInfo;
import soongsil.kidbean.server.member.dto.response.SolvedWordQuizListResponse;
import soongsil.kidbean.server.member.dto.response.UseWordInfo;
import soongsil.kidbean.server.member.dto.response.UseWordList;
import soongsil.kidbean.server.member.exception.MemberNotFoundException;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.member.dto.response.SolvedImageDetailResponse;
import soongsil.kidbean.server.member.dto.response.SolvedImageInfo;
import soongsil.kidbean.server.member.dto.response.SolvedAnswerQuizInfo;
import soongsil.kidbean.server.quiz.domain.AnswerQuizSolved;
import soongsil.kidbean.server.quiz.domain.Morpheme;
import soongsil.kidbean.server.quiz.domain.QuizSolved;
import soongsil.kidbean.server.quiz.domain.UseWord;
import soongsil.kidbean.server.quiz.domain.Word;
import soongsil.kidbean.server.quiz.exception.AnswerQuizNotFoundException;
import soongsil.kidbean.server.quiz.exception.QuizSolvedNotFoundException;
import soongsil.kidbean.server.quiz.exception.errorcode.QuizErrorCode;
import soongsil.kidbean.server.quiz.repository.MorphemeRepository;
import soongsil.kidbean.server.quiz.repository.AnswerQuizSolvedRepository;
import soongsil.kidbean.server.quiz.repository.QuizSolvedRepository;
import soongsil.kidbean.server.quiz.repository.UseWordRepository;
import soongsil.kidbean.server.quiz.repository.WordRepository;
import soongsil.kidbean.server.summary.domain.type.AgeGroup;
import soongsil.kidbean.server.summary.repository.AverageScoreRepository;
import soongsil.kidbean.server.summary.repository.QuizScoreRepository;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MypageService {

    private final QuizSolvedRepository quizSolvedRepository;
    private final AnswerQuizSolvedRepository answerQuizSolvedRepository;
    private final WordRepository wordRepository;
    private final MemberRepository memberRepository;
    private final QuizScoreRepository imageQuizScoreRepository;
    private final AverageScoreRepository averageScoreRepository;
    private final UseWordRepository useWordRepository;
    private final MorphemeRepository morphemeRepository;

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
     * @return
     */
    public SolvedWordDetailResponse solvedWordDetail(Long solvedId) {
        QuizSolved quizSolved = findQuizSolvedById(solvedId);
        List<Word> wordList = wordRepository.findAllByWordQuiz(quizSolved.getWordQuiz());

        return SolvedWordDetailResponse.from(quizSolved, wordList);
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
        List<Morpheme> morphemeList = morphemeRepository.findAllByAnswerQuizSolved(answerQuizSolved);
        List<UseWord> useWordList = useWordRepository.findAllByAnswerQuizSolved(answerQuizSolved);

        return SolvedAnswerDetailResponse.of(answerQuizSolved, morphemeList, useWordList);
    }

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

    private QuizSolved findQuizSolvedById(Long solvedId) {
        return quizSolvedRepository.findById(solvedId)
                .orElseThrow(() -> new QuizSolvedNotFoundException(QuizErrorCode.QUIZ_SOLVED_NOT_FOUND));
    }

    private AnswerQuizSolved findAnswerQuizSolvedById(Long recordId) {
        return answerQuizSolvedRepository.findById(recordId)
                .orElseThrow(() -> new AnswerQuizNotFoundException(QuizErrorCode.ANSWER_QUIZ_SOLVED_NOT_FOUND));
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
    }

    public UseWordList findAllUseWordList(Long memberId) {
        Member member = findMemberById(memberId);

        List<UseWordInfo> useWordInfoList = calculateCountOfUseWordFromMember(member);

        List<UseWordInfo> sortedReversedUseWOrdInfo = useWordInfoList.stream()
                .sorted(Comparator.comparingLong(UseWordInfo::count).reversed())
                .toList();

        return UseWordList.from(sortTop10UseWord(sortedReversedUseWOrdInfo));
    }

    private List<UseWordInfo> calculateCountOfUseWordFromMember(Member member) {
        List<UseWord> useWordList = useWordRepository.findAllByMemberFetchJoinUseWord(member);

        Set<Entry<String, Long>> ContentCountEntrySet = useWordList.stream()
                .collect(Collectors.groupingBy(UseWord::getWordName,
                        Collectors.summingLong(UseWord::getCount)))
                .entrySet();

        return ContentCountEntrySet.stream()
                .map(entry -> UseWordInfo.of(entry.getKey(), entry.getValue()))
                .toList();
    }

    private List<UseWordInfo> sortTop10UseWord(List<UseWordInfo> sortedList) {
        return sortedList.stream()
                .limit(10)
                .toList();
    }
}
