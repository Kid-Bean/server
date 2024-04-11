package soongsil.kidbean.server.quiz.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import soongsil.kidbean.server.quiz.application.vo.MorphemeVO;
import soongsil.kidbean.server.quiz.application.vo.OpenApiResponse;
import soongsil.kidbean.server.quiz.application.vo.UseWordVO;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OpenApiService {

    private final WebClient webClient;

    /**
     * 제출된 정답 String을 분석
     *
     * @param answerText 사용자가 제출한 답변
     * @return 분석 결과를 OpenApiResponse에 담아 전달
     */
    public OpenApiResponse analyzeAnswer(String answerText) {

        List<Map<String, Object>> responseBody = useWebClient(answerText);

        List<MorphemeVO> morphemeVOList = parseMorphemeAnalysis(responseBody);
        List<UseWordVO> useWordVOList = parseWordAnalysis(morphemeVOList);

        return OpenApiResponse.builder()
                .morphemeVOList(morphemeVOList)
                .useWordVOList(useWordVOList)
                .build();
    }

    @SuppressWarnings(value = "unchecked")
    private List<Map<String, Object>> useWebClient(String answerText) {

        String analysisCode = "morp"; // 언어 분석 코드 - 형태소 분석
        Map<String, Object> request = makeBaseRequest(analysisCode, answerText);

        //Blocking 방식으로 처리
        return webClient
                .post()
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Map.class)
                .map(responseBody -> {
                    Map<Object, Object> returnObject = (Map<Object, Object>) responseBody.get("return_object");
                    return (List<Map<String, Object>>) returnObject.get("sentence");
                })
                .block();
    }

    private static Map<String, Object> makeBaseRequest(String analysisCode, String text) {

        Map<String, Object> request = new HashMap<>();
        Map<String, String> argument = new HashMap<>();

        argument.put("analysis_code", analysisCode);
        argument.put("text", text);

        request.put("argument", argument);
        return request;
    }

    @SuppressWarnings(value = "unchecked")
    private List<MorphemeVO> parseMorphemeAnalysis(List<Map<String, Object>> sentences) {

        List<MorphemeVO> result = new ArrayList<>();

        for (Map<String, Object> sentence : sentences) {
            List<Map<String, Object>> morphemeList = (List<Map<String, Object>>) sentence.get("morp");

            for (Map<String, Object> morpheme : morphemeList) {
                result.add(MorphemeVO.builder()
                        .morpheme(morpheme.get("lemma").toString())
                        .type(morpheme.get("type").toString())
                        .build());
            }
        }

        return result;
    }

    private List<UseWordVO> parseWordAnalysis(List<MorphemeVO> morphemeVOList) {

        List<UseWordVO> result = new ArrayList<>();
        Map<String, Long> wordCountMap = new HashMap<>();

        for (MorphemeVO morphemeVO : morphemeVOList) {
            String type = morphemeVO.type();

            // 명사 형태소만 필터링
            if (type.equals("NNG") || type.equals("NNP") || type.equals("NNB")) {
                // 단어별로 등장 횟수 카운트
                String word = morphemeVO.morpheme();
                wordCountMap.put(word, wordCountMap.getOrDefault(word, 0L) + 1);
            }
        }

        // 카운트된 단어들을 결과 리스트에 추가
        for (Map.Entry<String, Long> entry : wordCountMap.entrySet()) {
            result.add(UseWordVO.builder()
                    .word(entry.getKey())
                    .count(entry.getValue())
                    .build());
        }

        return result;
    }
}