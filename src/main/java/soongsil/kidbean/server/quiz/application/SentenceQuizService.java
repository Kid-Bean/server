package soongsil.kidbean.server.quiz.application;


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
import soongsil.kidbean.server.quiz.domain.SentenceQuiz;
import soongsil.kidbean.server.quiz.dto.response.SentenceQuizResponse;
import soongsil.kidbean.server.quiz.exception.MemberNotFoundException;
import soongsil.kidbean.server.quiz.exception.SentenceQuizNotFoundException;
import soongsil.kidbean.server.quiz.repository.SentenceQuizRepository;
import soongsil.kidbean.server.quiz.repository.SentenceQuizWordRepository;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SentenceQuizService {

    private final SentenceQuizRepository sentenceQuizRepository;
    private final SentenceQuizWordRepository sentenceQuizWordRepository;
    private final MemberRepository memberRepository;

    /**
     * 랜덤 문제를 생성 후 멤버에게 전달
     *
     * @param memberId 문제를 풀 멤버
     * @return 랜덤 문제가 들어 있는 DTO
     */
    public SentenceQuizResponse selectRandomSentenceQuiz(Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        Page<SentenceQuiz> sentenceQuizPage = generateRandomSentenceQuizPage(member);

        SentenceQuiz sentenceQuiz = pageHasSentenceQuiz(sentenceQuizPage)
                .orElseThrow(SentenceQuizNotFoundException::new);

        return SentenceQuizResponse.from(sentenceQuiz);
    }

    /**
     * 랜덤 SentenceQuiz를 생성
     *
     * @param member 문제를 풀 멤버
     * @return 랜덤 SentenceQuiz가 있는 Page
     */
    private Page<SentenceQuiz> generateRandomSentenceQuizPage(Member member) {

        int divVal = getSentenceQuizCount(member);
        int idx = RandomUtil.getPositiveInt() % divVal;

        return sentenceQuizRepository.findByMemberOrMember_Role(
                member, Role.ADMIN, PageRequest.of(idx, 1));
    }

    /**
     * 해당 멤버 또는 role이 어드민으로 등록된 사람이 등록한 SentenceQuiz의 수 return
     *
     * @param member 문제를 풀고자 하는 멤버
     * @return SentenceQuiz의 수
     */
    private Integer getSentenceQuizCount(Member member) {
        return sentenceQuizRepository.countByMemberOrMember_Role(member, Role.ADMIN);
    }

    /**
     * 해당 페이지에 SentenceQuiz가 있는지 확인 후 Optional로 감싸 return
     *
     * @param sentenceQuizPage sentenceQuiz가 있는 Page
     * @return SentenceQuiz가 있는 Optional
     */
    private Optional<SentenceQuiz> pageHasSentenceQuiz(Page<SentenceQuiz> sentenceQuizPage) {
        if (sentenceQuizPage.hasContent()) {
            return Optional.of(sentenceQuizPage.getContent().get(0));
        } else {
            return Optional.empty();
        }
    }
}
