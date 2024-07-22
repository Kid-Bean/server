package soongsil.kidbean.server.tuning;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.StopWatch;
import org.springframework.web.context.WebApplicationContext;
import soongsil.kidbean.server.auth.application.jwt.JwtTokenProvider;
import soongsil.kidbean.server.auth.presentation.filter.JwtFilter;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.domain.type.Gender;
import soongsil.kidbean.server.member.domain.type.Role;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.quizsolve.domain.type.Level;
import soongsil.kidbean.server.quizsolve.domain.type.QuizCategory;
import soongsil.kidbean.server.quizsolve.dto.request.QuizSolvedListRequest;
import soongsil.kidbean.server.quizsolve.dto.request.QuizSolvedRequest;
import soongsil.kidbean.server.quizsolve.repository.QuizSolvedRepository;
import soongsil.kidbean.server.wordquiz.domain.Word;
import soongsil.kidbean.server.wordquiz.domain.WordQuiz;
import soongsil.kidbean.server.wordquiz.repository.WordQuizRepository;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class WordQuizConcurrentTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private QuizSolvedRepository quizSolvedRepository;

    @Autowired
    private WordQuizRepository wordQuizRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private JwtFilter jwtFilter;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeAll
    void setUp() {
        for (int i = 1; i <= 100; i++) {
            Member member = memberRepository.save(
                    Member.builder()
                            .email("email1")
                            .name("name1")
                            .socialId("socialId1")
                            .gender(Gender.MAN)
                            .role(Role.MEMBER)
                            .score(25L)
                            .build());

            WordQuiz wordQuiz = WordQuiz.builder()
                    .title("title")
                    .answer("answer")
                    .words(List.of(new Word("word1", null),
                            new Word("word1", null),
                            new Word("word1", null)))
                    .quizCategory(QuizCategory.ANIMAL)
                    .level(Level.BRONZE)
                    .member(member)
                    .build();

            wordQuizRepository.save(wordQuiz);
        }

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .addFilter(jwtFilter)
                .build();

        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("WordQuiz 풀기 테스트 - 동시성(데드락)")
    void solveWordQuizConcurrent() throws Exception {
        //given
        int loopCnt = 1;

        ExecutorService executorService = Executors.newFixedThreadPool(loopCnt);
        CountDownLatch latch = new CountDownLatch(loopCnt);

        StopWatch stopWatch = new StopWatch();

        //when
        stopWatch.start();

        for (long i = 1; i <= loopCnt; i++) {
            long quizId = i;

            executorService.execute(() -> {
                try {
                    String accessToken =
                            jwtTokenProvider.createAccessToken(memberRepository.findById(1L).orElseThrow());

                    QuizSolvedListRequest quizSolvedListRequest =
                            new QuizSolvedListRequest(List.of(
                                    QuizSolvedRequest.builder()
                                            .quizId(quizId)
                                            .answer("answer")
                                            .build(),
                                    QuizSolvedRequest.builder()
                                            .quizId(quizId + 1)
                                            .answer("answer")
                                            .build()));

                    mockMvc.perform(post("/quiz/word/solve")
                                    .header("Authorization", "Bearer " + accessToken)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(quizSolvedListRequest)))
                            .andExpect(MockMvcResultMatchers.status().isOk());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(); // 모든 스레드가 작업을 완료할 때까지 기다림
        stopWatch.stop();

        executorService.shutdown();

        //then
        log.info("===================결과 출력부===================");
        long dataCount = quizSolvedRepository.count();

        Assertions.assertThat(dataCount).isEqualTo(loopCnt * 2);

        log.info("데이터 수: {} 개", dataCount);
        log.info("반복 횟수: {} 회", loopCnt);
        log.info("총 소요 시간: {} ms", stopWatch.getTotalTimeMillis());
    }
}
