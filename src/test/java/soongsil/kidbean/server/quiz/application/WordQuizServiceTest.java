package soongsil.kidbean.server.quiz.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER;
import static soongsil.kidbean.server.quiz.fixture.WordQuizFixture.WORD_QUIZ;

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
import soongsil.kidbean.server.quiz.dto.response.WordQuizResponse;
import soongsil.kidbean.server.quiz.dto.response.WordQuizWordResponse;
import soongsil.kidbean.server.quiz.repository.WordQuizRepository;

@ExtendWith(MockitoExtension.class)
class WordQuizServiceTest {

    @Mock
    private WordQuizRepository wordQuizRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private WordQuizService wordQuizService;

    @Test
    @DisplayName("랜덤 WordQuiz 선택")
    void selectRandomWordQuiz() {

        //given
        given(memberRepository.findById(MEMBER.getMemberId()))
                .willReturn(Optional.of(MEMBER));
        given(wordQuizRepository.countByMemberOrMember_Role(MEMBER, Role.ADMIN))
                .willReturn(1);
        given(wordQuizRepository.findByMemberOrMember_Role(MEMBER, Role.ADMIN, PageRequest.of(0, 1)))
                .willReturn(new PageImpl<>(List.of(WORD_QUIZ)));

        //when
        WordQuizResponse WordQuizResponse = wordQuizService.selectRandomWordQuiz(MEMBER.getMemberId());

        //then
        assertThat(WordQuizResponse.title()).isEqualTo(WORD_QUIZ.getTitle());
        assertThat(WordQuizResponse.words()).isEqualTo(WORD_QUIZ.getWords().stream()
                .map(WordQuizWordResponse::from)
                .toList());
    }
}