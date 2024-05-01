package soongsil.kidbean.server.quiz.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER1;
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
import org.springframework.data.domain.Pageable;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.quiz.dto.response.WordQuizSolveListResponse;
import soongsil.kidbean.server.quiz.dto.response.WordResponse;
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
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(MEMBER1));
        given(wordQuizRepository.countByMemberOrAdmin(any(Member.class))).willReturn(4);
        given(wordQuizRepository.findSinglePageByMember(any(Member.class), any(Pageable.class)))
                .willReturn(new PageImpl<>(List.of(WORD_QUIZ)));

        //when
        WordQuizSolveListResponse wordQuizSolveListResponse =
                wordQuizService.selectRandomWordQuiz(MEMBER1.getMemberId(), 3);

        //then
        assertThat(wordQuizSolveListResponse.wordQuizSolveResponseList().get(0).title())
                .isEqualTo(WORD_QUIZ.getTitle());
        assertThat(wordQuizSolveListResponse.wordQuizSolveResponseList().get(0).words())
                .isEqualTo(WORD_QUIZ.getWords().stream()
                        .map(WordResponse::from)
                        .toList());
    }
}