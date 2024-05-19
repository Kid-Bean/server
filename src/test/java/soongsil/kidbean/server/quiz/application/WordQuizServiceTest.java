package soongsil.kidbean.server.quiz.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER1;
import static soongsil.kidbean.server.quiz.fixture.WordFixture.WORD1;
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
import soongsil.kidbean.server.quiz.domain.WordQuiz;
import soongsil.kidbean.server.quiz.dto.request.WordQuizUpdateRequest;
import soongsil.kidbean.server.quiz.dto.request.WordQuizUploadRequest;
import soongsil.kidbean.server.quiz.dto.request.WordRequest;
import soongsil.kidbean.server.quiz.dto.response.WordQuizMemberDetailResponse;
import soongsil.kidbean.server.quiz.dto.response.WordQuizMemberResponse;
import soongsil.kidbean.server.quiz.dto.response.WordQuizSolveListResponse;
import soongsil.kidbean.server.quiz.dto.response.WordResponse;
import soongsil.kidbean.server.quiz.repository.WordQuizRepository;
import soongsil.kidbean.server.quiz.repository.WordRepository;

@ExtendWith(MockitoExtension.class)
class WordQuizServiceTest {

    @Mock
    private WordQuizRepository wordQuizRepository;

    @Mock
    private WordRepository wordRepository;

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

    @Test
    @DisplayName("WordQuiz 상세 정보 가져오기")
    void getWordQuizById() {
        // given
        given(wordQuizRepository.findByQuizIdAndMember_MemberId(anyLong(), anyLong())).willReturn(Optional.of(WORD_QUIZ));

        // when
        WordQuizMemberDetailResponse response = wordQuizService.getWordQuizById(anyLong(), anyLong());

        // then
        assertThat(response.title()).isEqualTo(WORD_QUIZ.getTitle());
    }

    @Test
    @DisplayName("WordQuiz 리스트 가져오기")
    void getAllWordQuizByMember() {
        // given
        given(wordQuizRepository.findAllByMember_MemberId(anyLong())).willReturn(List.of(WORD_QUIZ));

        // when
        List<WordQuizMemberResponse> responses = wordQuizService.getAllWordQuizByMember(anyLong());

        // then
        assertThat(responses).hasSize(1);
    }

    @Test
    @DisplayName("WordQuiz 등록하기")
    void uploadWordQuiz() {
        // given
        WordRequest wordRequest = new WordRequest("단어");
        WordQuizUploadRequest request = new WordQuizUploadRequest("테스트 제목", "테스트 정답", List.of(wordRequest));

        given(memberRepository.findById(anyLong())).willReturn(Optional.of(MEMBER1));

        // when
        wordQuizService.uploadWordQuiz(request, anyLong());

        // then
        verify(wordQuizRepository, times(1)).save(any(WordQuiz.class));
    }

    @Test
    @DisplayName("WordQuiz 수정하기")
    void updateWordQuiz() {
        // given
        WordRequest wordRequest = new WordRequest("단어");
        WordQuizUpdateRequest request = new WordQuizUpdateRequest("테스트 제목 수정", "테스트 정답 수정", List.of(wordRequest));

        given(wordQuizRepository.findById(anyLong())).willReturn(Optional.ofNullable(WORD_QUIZ));
        given(wordRepository.findAllByWordQuiz(any(WordQuiz.class))).willReturn(List.of(WORD1));

        // when
        wordQuizService.updateWordQuiz(request, anyLong());

        // then
        assertThat(WORD_QUIZ.getTitle()).isEqualTo(request.title());
    }

    @Test
    @DisplayName("WordQuiz 삭제하기")
    void deleteWordQuiz() {
        // given
        given(wordQuizRepository.findById(anyLong())).willReturn(Optional.of(WORD_QUIZ));

        // when
        wordQuizService.deleteWordQuiz(anyLong());

        // then
        verify(wordQuizRepository, times(1)).delete(any(WordQuiz.class));
    }
}