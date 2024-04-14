package soongsil.kidbean.server.quiz.application;


import ch.qos.logback.core.testUtil.RandomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.domain.type.Role;
import soongsil.kidbean.server.member.exception.MemberNotFoundException;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.quiz.domain.Word;
import soongsil.kidbean.server.quiz.domain.WordQuiz;
import soongsil.kidbean.server.quiz.dto.request.QuizSolvedRequest;
import soongsil.kidbean.server.quiz.dto.request.WordQuizUpdateRequest;
import soongsil.kidbean.server.quiz.dto.request.WordQuizUploadRequest;
import soongsil.kidbean.server.quiz.dto.response.WordQuizMemberDetailResponse;
import soongsil.kidbean.server.quiz.dto.response.WordQuizMemberResponse;
import soongsil.kidbean.server.quiz.dto.response.WordQuizResponse;
import soongsil.kidbean.server.quiz.dto.response.WordQuizSolveScoreResponse;
import soongsil.kidbean.server.quiz.exception.WordQuizNotFoundException;
import soongsil.kidbean.server.quiz.repository.WordQuizRepository;
import soongsil.kidbean.server.quiz.repository.WordRepository;

import java.util.List;
import java.util.Optional;

import static soongsil.kidbean.server.member.exception.errorcode.MemberErrorCode.MEMBER_NOT_FOUND;
import static soongsil.kidbean.server.quiz.application.vo.QuizType.WORD_QUIZ;
import static soongsil.kidbean.server.quiz.exception.errorcode.QuizErrorCode.WORD_QUIZ_NOT_FOUND;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WordQuizService {

    private final MemberRepository memberRepository;
    private final WordQuizRepository wordQuizRepository;
    private final QuizSolvedService quizSolvedService;
    private final WordRepository wordRepository;

    /**
     * 랜덤 문제를 생성 후 멤버에게 전달
     *
     * @param memberId 문제를 풀 멤버
     * @return 랜덤 문제가 들어 있는 DTO
     */
    public WordQuizResponse selectRandomWordQuiz(Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
        Page<WordQuiz> WordQuizPage = generateRandomWordQuizPage(member);

        WordQuiz WordQuiz = pageHasWordQuiz(WordQuizPage)
                .orElseThrow(() -> new WordQuizNotFoundException(WORD_QUIZ_NOT_FOUND));

        return WordQuizResponse.from(WordQuiz);
    }

    @Transactional
    public WordQuizSolveScoreResponse solveWordQuizzes(List<QuizSolvedRequest> quizSolvedRequestList, Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));

        return WordQuizSolveScoreResponse.scoreFrom(
                quizSolvedService.solveQuizzes(quizSolvedRequestList, member, WORD_QUIZ));
    }

    /**
     * 랜덤 WordQuiz를 생성
     *
     * @param member 문제를 풀 멤버
     * @return 랜덤 WordQuiz가 있는 Page
     */
    private Page<WordQuiz> generateRandomWordQuizPage(Member member) {

        int divVal = getWordQuizCount(member);
        int idx = RandomUtil.getPositiveInt() % divVal;

        return wordQuizRepository.findByMemberOrMember_Role(member, Role.ADMIN, PageRequest.of(idx, 1));
    }

    /**
     * 해당 멤버 또는 role이 어드민으로 등록된 사람이 등록한 WordQuiz의 수 return
     *
     * @param member 문제를 풀고자 하는 멤버
     * @return WordQuiz의 수
     */
    private Integer getWordQuizCount(Member member) {
        return wordQuizRepository.countByMemberOrMember_Role(member, Role.ADMIN);
    }

    /**
     * 해당 페이지에 WordQuiz가 있는지 확인 후 Optional로 감싸 return
     *
     * @param WordQuizPage WordQuiz가 있는 Page
     * @return WordQuiz가 있는 Optional
     */
    private Optional<WordQuiz> pageHasWordQuiz(Page<WordQuiz> WordQuizPage) {
        if (WordQuizPage.hasContent()) {
            return Optional.of(WordQuizPage.getContent().get(0));
        } else {
            return Optional.empty();
        }
    }

    public WordQuizMemberDetailResponse getWordQuizById(Long memberId, Long quizId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
        WordQuiz WordQuiz = wordQuizRepository.findByQuizIdAndMember(quizId, member)
                .orElseThrow(() -> new WordQuizNotFoundException(WORD_QUIZ_NOT_FOUND));

        return WordQuizMemberDetailResponse.from(WordQuiz);
    }

    public List<WordQuizMemberResponse> getAllWordQuizByMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));

        return wordQuizRepository.findAllByMember(member)
                .stream()
                .map(WordQuizMemberResponse::from)
                .toList();
    }

    @Transactional
    public void uploadWordQuiz(WordQuizUploadRequest request, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));

        WordQuiz wordQuiz = request.toWordQuiz(member);

        wordQuizRepository.save(wordQuiz);
    }

    @Transactional
    public void updateWordQuiz(WordQuizUpdateRequest request, Long memberId, Long quizId) {
        WordQuiz wordQuiz = wordQuizRepository.findById(quizId)
                .orElseThrow(() -> new WordQuizNotFoundException(WORD_QUIZ_NOT_FOUND));

        List<Word> wordList = wordRepository.findAllByWordQuiz(wordQuiz);

        updateWords(request, wordList);

        wordQuiz.update(request.title(), request.answer());
    }

    private static void updateWords(WordQuizUpdateRequest request, List<Word> wordList) {
        int i = 0;

        for (Word originalWord : wordList) {
            String newWord = request.words().get(i);
            originalWord.update(newWord);
            i++;
        }
    }

    @Transactional
    public void deleteWordQuiz(Long memberId, Long quizId) {
        WordQuiz wordQuiz = wordQuizRepository.findById(quizId)
                .orElseThrow(() -> new WordQuizNotFoundException(WORD_QUIZ_NOT_FOUND));

        wordQuizRepository.delete(wordQuiz);
        wordQuizRepository.flush();
    }
}
