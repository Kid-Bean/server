package soongsil.kidbean.server.quiz.application;

import static soongsil.kidbean.server.quiz.exception.errorcode.QuizErrorCode.SENTENCE_QUIZ_NOT_FOUND;

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
import soongsil.kidbean.server.member.exception.MemberNotFoundException;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.quiz.domain.AnswerQuiz;
import soongsil.kidbean.server.quiz.dto.response.AnswerQuizResponse;
import soongsil.kidbean.server.quiz.exception.SentenceQuizNotFoundException;
import soongsil.kidbean.server.quiz.repository.AnswerQuizRepository;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AnswerQuizService {

    private final AnswerQuizRepository answerQuizRepository;
    private final MemberRepository memberRepository;

    /**
     * 랜덤 문제를 생성 후 멤버에게 전달
     *
     * @param memberId 문제를 풀 멤버
     * @return 랜덤 문제가 들어 있는 DTO
     */
    public AnswerQuizResponse selectRandomAnswerQuiz(Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        Page<AnswerQuiz> answerQuizPage = generateRandomAnswerQuizPage(member);

        AnswerQuiz answerQuiz = pageHasAnswerQuiz(answerQuizPage)
                .orElseThrow(() -> new SentenceQuizNotFoundException(SENTENCE_QUIZ_NOT_FOUND));

        return AnswerQuizResponse.from(answerQuiz);
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
}
