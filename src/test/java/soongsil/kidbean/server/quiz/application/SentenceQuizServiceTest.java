package soongsil.kidbean.server.quiz.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER;
import static soongsil.kidbean.server.quiz.fixture.SentenceQuizFixture.SENTENCE_QUIZ;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import soongsil.kidbean.server.member.domain.type.Role;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.quiz.dto.response.SentenceQuizResponse;
import soongsil.kidbean.server.quiz.dto.response.SentenceQuizWordResponse;
import soongsil.kidbean.server.quiz.repository.SentenceQuizRepository;

@ExtendWith(MockitoExtension.class)
class SentenceQuizServiceTest {

    @Mock
    private SentenceQuizRepository sentenceQuizRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private SentenceQuizService sentenceQuizService;

    @Test
    void 랜덤_SentenceQuiz_선택() {

        //given
        given(memberRepository.findById(MEMBER.getMemberId()))
                .willReturn(Optional.of(MEMBER));
        given(sentenceQuizRepository.countByMemberOrMember_Role(MEMBER, MEMBER.getRole()))
                .willReturn(1);
        given(sentenceQuizRepository.findByMemberOrMember_Role(MEMBER, Role.ADMIN, PageRequest.of(0, 1)))
                .willReturn(new PageImpl<>(List.of(SENTENCE_QUIZ)));

        //when
        SentenceQuizResponse sentenceQuizResponse = sentenceQuizService.selectRandomSentenceQuiz(MEMBER.getMemberId());

        //then
        assertThat(sentenceQuizResponse.title()).isEqualTo(SENTENCE_QUIZ.getTitle());
        assertThat(sentenceQuizResponse.words()).isEqualTo(SENTENCE_QUIZ.getWords().stream()
                .map(SentenceQuizWordResponse::from)
                .toList());
    }
}