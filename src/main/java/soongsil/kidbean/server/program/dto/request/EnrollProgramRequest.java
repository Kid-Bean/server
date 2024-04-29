package soongsil.kidbean.server.program.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.program.domain.Program;
import soongsil.kidbean.server.program.domain.type.ProgramCategory;
import soongsil.kidbean.server.quiz.domain.WordQuiz;
import soongsil.kidbean.server.quiz.domain.type.Level;

import java.util.ArrayList;


public record EnrollProgramRequest(
        Long programId,

        @NotNull (message = "선생님 이름을 입력해주세요")
        String teacherName,
        @NotNull (message = "제목을 입력해주세요")
        String title,
        @NotNull (message = "장소를 입력해주세요")
        String place,
        @NotNull (message = "전화번호를 입력해주세요")
        String phoneNumber,
        @NotNull (message = "내용을 입력해주세요")
        String content

) {

}
