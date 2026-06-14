package soongsil.kidbean.server.member.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER1;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soongsil.kidbean.server.answerquiz.application.OpenApiService;
import soongsil.kidbean.server.answerquiz.domain.AnswerQuiz;
import soongsil.kidbean.server.member.dto.response.SolvedAnswerQuizListResponse;
import soongsil.kidbean.server.member.dto.response.UseWordList;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.quizsolve.application.AnalyzeMorphemeService;
import soongsil.kidbean.server.quizsolve.domain.AnswerQuizSolved;
import soongsil.kidbean.server.quizsolve.domain.UseWord;
import soongsil.kidbean.server.quizsolve.repository.AnswerQuizSolvedRepository;
import soongsil.kidbean.server.quizsolve.repository.UseWordRepository;

@ExtendWith(MockitoExtension.class)
class VoiceSolvedResultServiceTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private UseWordRepository useWordRepository;
    @Mock
    private AnswerQuizSolvedRepository answerQuizSolvedRepository;
    @Mock
    private AnalyzeMorphemeService analyzeMorphemeService;
    @Mock
    private OpenApiService openApiService;

    @InjectMocks
    private VoiceSolvedResultService voiceSolvedResultService;

    @Test
    @DisplayName("가장 많이 사용한 단어 상위 5개를 카운트와 함께 반환한다")
    void findTop5UseWordList() {
        // given
        List<UseWord> useWords = List.of(
                UseWord.builder().wordName("사과").count(3L).member(MEMBER1).build(),
                UseWord.builder().wordName("바나나").count(2L).member(MEMBER1).build()
        );
        given(memberRepository.getByIdOrThrow(MEMBER1.getMemberId())).willReturn(MEMBER1);
        given(useWordRepository.findTop5ByMemberOrderByCountDesc(MEMBER1)).willReturn(useWords);

        // when
        UseWordList result = voiceSolvedResultService.findTop5UseWordList(MEMBER1.getMemberId());

        // then
        assertThat(result.wordInfoList()).hasSize(2);
        assertThat(result.wordInfoList()).extracting("word")
                .containsExactly("사과", "바나나");
        assertThat(result.wordInfoList()).extracting("count")
                .containsExactly(3L, 2L);
    }

    @Test
    @DisplayName("회원이 푼 음성 퀴즈 목록을 반환한다")
    void findSolvedAnswerQuiz() {
        // given
        AnswerQuiz answerQuiz = AnswerQuiz.builder().title("인사하기").build();
        AnswerQuizSolved solved = AnswerQuizSolved.builder()
                .sentenceAnswer("안녕하세요")
                .member(MEMBER1)
                .answerQuiz(answerQuiz)
                .build();

        given(memberRepository.getByIdOrThrow(MEMBER1.getMemberId())).willReturn(MEMBER1);
        given(answerQuizSolvedRepository.findAllByMember(MEMBER1)).willReturn(List.of(solved));

        // when
        SolvedAnswerQuizListResponse result = voiceSolvedResultService.findSolvedAnswerQuiz(MEMBER1.getMemberId());

        // then
        assertThat(result.solvedList()).hasSize(1);
        assertThat(result.solvedList().get(0).title()).isEqualTo("인사하기");
    }
}
