package soongsil.kidbean.server.quiz.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.quiz.domain.SentenceQuiz;
import soongsil.kidbean.server.quiz.dto.response.SentenceQuizMemberDetailResponse;
import soongsil.kidbean.server.quiz.exception.MemberNotFoundException;
import soongsil.kidbean.server.quiz.exception.SentenceQuizNotFoundException;
import soongsil.kidbean.server.quiz.repository.SentenceQuizRepository;
import soongsil.kidbean.server.quiz.repository.SentenceQuizWordRepository;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SentenceQuizService {

    private final MemberRepository memberRepository;
    private final SentenceQuizRepository sentenceQuizRepository;
    private final SentenceQuizWordRepository sentenceQuizWordRepository;

    public SentenceQuizMemberDetailResponse getSentenceQuizById(Long memberId, Long quizId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        SentenceQuiz sentenceQuiz = sentenceQuizRepository.findByQuizIdAndMember(quizId, member)
                .orElseThrow(SentenceQuizNotFoundException::new);

        return SentenceQuizMemberDetailResponse.from(sentenceQuiz);
    }
}
