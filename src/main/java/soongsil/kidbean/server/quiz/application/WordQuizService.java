package soongsil.kidbean.server.quiz.application;


import static soongsil.kidbean.server.quiz.exception.errorcode.QuizErrorCode.Word_QUIZ_NOT_FOUND;

import ch.qos.logback.core.testUtil.RandomUtil;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.domain.type.Role;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.quiz.domain.WordQuiz;
import soongsil.kidbean.server.quiz.dto.response.WordQuizResponse;
import soongsil.kidbean.server.member.exception.MemberNotFoundException;
import soongsil.kidbean.server.quiz.exception.WordQuizNotFoundException;
import soongsil.kidbean.server.quiz.dto.response.WordQuizMemberDetailResponse;
import soongsil.kidbean.server.quiz.repository.WordQuizRepository;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WordQuizService {

    private final MemberRepository memberRepository;
    private final WordQuizRepository wordQuizRepository;

    /**
     * 랜덤 문제를 생성 후 멤버에게 전달
     *
     * @param memberId 문제를 풀 멤버
     * @return 랜덤 문제가 들어 있는 DTO
     */
    public WordQuizResponse selectRandomWordQuiz(Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        Page<WordQuiz> WordQuizPage = generateRandomWordQuizPage(member);

        WordQuiz WordQuiz = pageHasWordQuiz(WordQuizPage)
                .orElseThrow(() -> new WordQuizNotFoundException(Word_QUIZ_NOT_FOUND));

        return WordQuizResponse.from(WordQuiz);
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
                .orElseThrow(MemberNotFoundException::new);
        WordQuiz WordQuiz = wordQuizRepository.findByQuizIdAndMember(quizId, member)
                .orElseThrow(() -> new WordQuizNotFoundException(Word_QUIZ_NOT_FOUND));

        return WordQuizMemberDetailResponse.from(WordQuiz);
    }
}
