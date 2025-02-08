package soongsil.kidbean.server.wordquiz.application;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.exception.MemberNotFoundException;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.quizsolve.application.QuizSolvedService;
import soongsil.kidbean.server.quizsolve.dto.request.QuizSolvedRequest;
import soongsil.kidbean.server.wordquiz.domain.Word;
import soongsil.kidbean.server.wordquiz.domain.WordQuiz;
import soongsil.kidbean.server.wordquiz.dto.request.WordQuizUpdateRequest;
import soongsil.kidbean.server.wordquiz.dto.request.WordQuizUploadRequest;
import soongsil.kidbean.server.wordquiz.dto.response.*;
import soongsil.kidbean.server.wordquiz.exception.WordQuizNotFoundException;
import soongsil.kidbean.server.wordquiz.repository.WordQuizRepository;
import soongsil.kidbean.server.wordquiz.repository.WordRepository;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static soongsil.kidbean.server.member.exception.errorcode.MemberErrorCode.MEMBER_NOT_FOUND;
import static soongsil.kidbean.server.quizsolve.application.vo.QuizType.WORD_QUIZ;
import static soongsil.kidbean.server.wordquiz.exception.errorcode.WordQuizErrorCode.WORD_QUIZ_NOT_FOUND;

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
    public WordQuizSolveListResponse selectRandomWordQuiz(Long memberId, Integer quizNum) {
        long count = wordQuizRepository.countByMemberId(memberId);
        int randomOffset = ThreadLocalRandom.current().nextInt((int) (count / quizNum));

        List<Long> randomQuizIds = wordQuizRepository.findRandomQuizIds(memberId, PageRequest.of(randomOffset, quizNum));

        List<WordQuizSolveResponse> wordQuizSolveResponseList =
                wordQuizRepository.findByIdsWithWords(randomQuizIds).stream()
                        .map(WordQuizSolveResponse::from)
                        .toList();

        return new WordQuizSolveListResponse(wordQuizSolveResponseList);
    }

    private int getWordQuizCount(Member member) {
        return wordQuizRepository.countByMemberOrAdmin(member);
    }

    private WordQuiz generateRandomWordQuizPage(Member member, int quizIdx) {
        return wordQuizRepository.findSingleResultByMember(member, PageRequest.of(quizIdx, 1)).get(0);
    }

    @Transactional
    public WordQuizSolveScoreResponse solveWordQuizzes(List<QuizSolvedRequest> quizSolvedRequestList, Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));

        return WordQuizSolveScoreResponse.scoreFrom(
                quizSolvedService.solveQuizzes(quizSolvedRequestList, member, WORD_QUIZ));
    }

    public WordQuizMemberDetailResponse getWordQuizById(Long memberId, Long quizId) {
        WordQuiz wordQuiz = wordQuizRepository.findByQuizIdAndMember_MemberId(quizId, memberId)
                .orElseThrow(() -> new WordQuizNotFoundException(WORD_QUIZ_NOT_FOUND));

        return WordQuizMemberDetailResponse.from(wordQuiz);
    }

    public List<WordQuizMemberResponse> getAllWordQuizByMember(Long memberId) {
        return wordQuizRepository.findAllByMember_MemberId(memberId).stream()
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
    public void updateWordQuiz(WordQuizUpdateRequest request, Long quizId) {
        WordQuiz wordQuiz = wordQuizRepository.findById(quizId)
                .orElseThrow(() -> new WordQuizNotFoundException(WORD_QUIZ_NOT_FOUND));

        List<Word> wordList = wordRepository.findAllByWordQuiz(wordQuiz);
        updateWords(request, wordList);

        wordQuiz.updateWordQuiz(request.title(), request.answer());
    }

    @Transactional
    public void deleteWordQuiz(Long quizId) {
        WordQuiz wordQuiz = wordQuizRepository.findById(quizId)
                .orElseThrow(() -> new WordQuizNotFoundException(WORD_QUIZ_NOT_FOUND));

        wordQuizRepository.delete(wordQuiz);
    }

    private void updateWords(WordQuizUpdateRequest request, List<Word> wordList) {
        int i = 0;

        for (Word originalWord : wordList) {
            String newWord = request.words().get(i).content();
            originalWord.update(newWord);
            i++;
        }
    }
}
