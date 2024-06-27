package soongsil.kidbean.server.quizsolve.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Builder;

@Builder
public record QuizSolvedListRequest(
        @Valid
        @Size(min = 1, max = 5)
        List<QuizSolvedRequest> quizSolvedRequestList
) {
}
