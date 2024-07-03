package soongsil.kidbean.server.imagequiz.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER1;

import java.lang.reflect.Field;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soongsil.kidbean.server.imagequiz.repository.ImageQuizRepository;

@ExtendWith(MockitoExtension.class)
class ImageQuizCountCacheTest {

    @Mock
    private ImageQuizRepository imageQuizRepository;

    @InjectMocks
    private ImageQuizCountCache imageQuizCountCache;

    @BeforeEach
    public void setUp() throws Exception {
        // 캐시 초기화
        Field cacheField = ImageQuizCountCache.class.getDeclaredField("imageQuizCountCache");
        cacheField.setAccessible(true);
        ((Map<Long, Long>) cacheField.get(imageQuizCountCache)).clear();
    }

    @Test
    @DisplayName("이미지 퀴즈 푼 횟수 증가")
    void plusCount() {
        //given
        Long memberId = MEMBER1.getMemberId();
        given(imageQuizRepository.countByMemberIdAndDefaultProblem(memberId)).willReturn(0L);

        //when
        imageQuizCountCache.plusCount(memberId);    //Map에 값이 없으므로 값 추가 - 0
        imageQuizCountCache.plusCount(memberId);    //plusCount 실행

        //then
        assertThat(imageQuizCountCache.get(memberId)).isEqualTo(1);
    }

    @Test
    @DisplayName("이미지 퀴즈 푼 횟수 감소")
    void minusCount() {
        //given
        Long memberId = MEMBER1.getMemberId();
        given(imageQuizRepository.countByMemberIdAndDefaultProblem(memberId)).willReturn(1L);

        //when
        imageQuizCountCache.minusCount(memberId);    //Map에 값이 없으므로 값 추가 - 1
        imageQuizCountCache.minusCount(memberId);    //minusCount 실행

        //then
        assertThat(imageQuizCountCache.get(memberId)).isEqualTo(0);
    }

    @Test
    @DisplayName("이미지 퀴즈 푼 횟수 조회")
    void get() {
        //given
        Long memberId = MEMBER1.getMemberId();
        given(imageQuizRepository.countByMemberIdAndDefaultProblem(memberId)).willReturn(1L);

        //when
        imageQuizCountCache.get(memberId);    //Map에 값이 없으므로 값 추가 - 1

        //then
        assertThat(imageQuizCountCache.get(memberId)).isEqualTo(1);
    }

}