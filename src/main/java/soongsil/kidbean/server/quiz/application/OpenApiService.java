package soongsil.kidbean.server.quiz.application;

import static soongsil.kidbean.server.quiz.exception.errorcode.QuizErrorCode.OPEN_API_IO_EXCEPTION;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.quiz.application.vo.Morpheme;
import soongsil.kidbean.server.quiz.application.vo.OpenApiResponse;
import soongsil.kidbean.server.quiz.application.vo.WordCount;
import soongsil.kidbean.server.quiz.exception.OpenApiIOException;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OpenApiService {

    @Value("${openApi.accessKey}")
    private String accessKey;

    @Value("${openApi.url}")
    private String openApiURL;

    public OpenApiResponse analyzeAnswer(String answerText) {

        List<Map<String, Object>> responseBody = analysisRequest(answerText);

        List<Morpheme> morphemeList = parseMorphemeAnalysis(responseBody);
        List<WordCount> wordCountList = parseWordAnalysis(morphemeList);

        return OpenApiResponse.builder()
                .morphemeList(morphemeList)
                .wordCountList(wordCountList)
                .build();
    }

    @SuppressWarnings(value = "unchecked")
    private List<Map<String, Object>> analysisRequest(String answerText) {

        String analysisCode = "morp";        // 언어 분석 코드 - 형태소 분석
        Gson gson = new Gson();

        List<Map<String, Object>> sentences;
        Map<String, Object> request = makeBaseRequest(analysisCode, answerText);

        try {
            InputStream is = getInputStream(gson, request);

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }

            String responseBodyJson = sb.toString();
            Map<Object, Object> responseBody = gson.fromJson(responseBodyJson, Map.class);

            Map<Object, Object> returnObject = (Map<Object, Object>) responseBody.get("return_object");
            sentences = (List<Map<String, Object>>) returnObject.get("sentence");

        } catch (IOException e) {
            throw new OpenApiIOException(OPEN_API_IO_EXCEPTION);
        }
        return sentences;
    }

    private static Map<String, Object> makeBaseRequest(String analysisCode, String text) {

        Map<String, Object> request = new HashMap<>();
        Map<String, String> argument = new HashMap<>();

        argument.put("analysis_code", analysisCode);
        argument.put("text", text);

        request.put("argument", argument);
        return request;
    }

    private InputStream getInputStream(Gson gson, Map<String, Object> request) throws IOException {

        URL url = new URL(openApiURL);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        con.setRequestProperty("Authorization", accessKey);

        //request 내용을 서버로 전송
        try (OutputStream os = con.getOutputStream();
             DataOutputStream wr = new DataOutputStream(os)) {
            wr.write(gson.toJson(request).getBytes(StandardCharsets.UTF_8));
            wr.flush();
        }

        int responseCode = con.getResponseCode();
        // http 요청 오류 시 처리
        if (responseCode != 200) {
            throw new IOException();
        }

        //서버의 InputStream 반환
        return con.getInputStream();
    }

    @SuppressWarnings(value = "unchecked")
    private List<Morpheme> parseMorphemeAnalysis(List<Map<String, Object>> sentences) {

        List<Morpheme> result = new ArrayList<>();

        for (Map<String, Object> sentence : sentences) {
            List<Map<String, Object>> morphemeList = (List<Map<String, Object>>) sentence.get("morp");

            for (Map<String, Object> morpheme : morphemeList) {
                result.add(Morpheme.builder()
                        .morpheme(morpheme.get("lemma").toString())
                        .type(morpheme.get("type").toString())
                        .build());
            }
        }

        return result;
    }

    private List<WordCount> parseWordAnalysis(List<Morpheme> morphemeList) {

        List<WordCount> result = new ArrayList<>();
        Map<String, Integer> wordCountMap = new HashMap<>();

        for (Morpheme morpheme : morphemeList) {
            String type = morpheme.type();

            // 명사 형태소만 필터링
            if (type.equals("NNG") || type.equals("NNP") || type.equals("NNB")) {
                // 단어별로 등장 횟수 카운트
                String word = morpheme.morpheme();
                wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
            }
        }

        // 카운트된 단어들을 결과 리스트에 추가
        for (Map.Entry<String, Integer> entry : wordCountMap.entrySet()) {
            result.add(WordCount.builder()
                    .word(entry.getKey())
                    .count(entry.getValue())
                    .build());
        }

        return result;
    }
}