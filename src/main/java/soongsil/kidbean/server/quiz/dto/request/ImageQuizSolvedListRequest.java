package soongsil.kidbean.server.quiz.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Builder;

@Builder
public record ImageQuizSolvedListRequest(
        @Valid
        @Size(max = 5)
        List<ImageQuizSolvedRequest> imageQuizSolvedRequestList
) {
}
