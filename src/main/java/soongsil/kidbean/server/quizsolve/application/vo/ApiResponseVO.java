package soongsil.kidbean.server.quizsolve.application.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;

@JsonNaming(SnakeCaseStrategy.class)
public record ApiResponseVO(ReturnObject returnObject) {
    public record ReturnObject(List<Sentence> sentence) {
        public record Sentence(List<MorphemeVO> morp) {
            public record MorphemeVO(String lemma, String type) {

            }
        }
    }
}






