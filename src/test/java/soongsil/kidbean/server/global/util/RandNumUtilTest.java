package soongsil.kidbean.server.global.util;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RandNumUtilTest {

    @Test
    @DisplayName("랜덤 숫자 생성 테스트")
    void test() {
        //given
        int startNum = 1;
        int endNum = 10;
        int size = 5;

        //when
        List<Integer> integerList = RandNumUtil.generateRandomNumbers(startNum, endNum, size);

        //then
        Assertions.assertThat(integerList.size()).isEqualTo(size);
    }
}