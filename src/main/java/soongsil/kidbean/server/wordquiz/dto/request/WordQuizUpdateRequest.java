package soongsil.kidbean.server.wordquiz.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record WordQuizUpdateRequest(
        @NotNull(message = "제목을 입력해주세요.")
        String title,
        @NotNull(message = "정답을 입력해주세요.")
        String answer,
        @Size(min = 4, max = 4, message = "4개의 단어를 입력해주세요")
        List<WordRequest> words
) {
}
