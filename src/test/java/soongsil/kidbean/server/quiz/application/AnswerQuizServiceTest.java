package soongsil.kidbean.server.quiz.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER;
import static soongsil.kidbean.server.quiz.fixture.AnswerQuizFixture.ANSWER_QUIZ;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import soongsil.kidbean.server.member.domain.type.Role;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.quiz.dto.response.AnswerQuizResponse;
import soongsil.kidbean.server.quiz.repository.AnswerQuizRepository;

@ExtendWith(MockitoExtension.class)
class AnswerQuizServiceTest {

    @Mock
    private AnswerQuizRepository answerQuizRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private AnswerQuizService answerQuizService;

    @Test
    @DisplayName("랜덤 SentenceQuiz 선택")
    void selectRandomSentenceQuiz() {

        //given
        given(memberRepository.findById(MEMBER.getMemberId()))
                .willReturn(Optional.of(MEMBER));
        given(answerQuizRepository.countByMemberOrMember_Role(MEMBER, Role.ADMIN))
                .willReturn(1);
        given(answerQuizRepository.findByMemberOrMember_Role(MEMBER, Role.ADMIN, PageRequest.of(0, 1)))
                .willReturn(new PageImpl<>(List.of(ANSWER_QUIZ)));

        //when
        AnswerQuizResponse answerQuizResponse = answerQuizService.selectRandomAnswerQuiz(MEMBER.getMemberId());

        //then
        assertThat(answerQuizResponse.question()).isEqualTo(ANSWER_QUIZ.getQuestion());
    }
}