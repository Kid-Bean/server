package soongsil.kidbean.server.quizsolve.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import soongsil.kidbean.server.global.application.S3Uploader;
import soongsil.kidbean.server.global.domain.S3Info;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quizsolve.application.vo.OpenApiResponse;
import soongsil.kidbean.server.answerquiz.domain.AnswerQuiz;
import soongsil.kidbean.server.quizsolve.domain.AnswerQuizSolved;
import soongsil.kidbean.server.quizsolve.domain.UseWord;
import soongsil.kidbean.server.quizsolve.repository.AnswerQuizSolvedRepository;
import soongsil.kidbean.server.quizsolve.repository.UseWordRepository;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AnswerQuizSolvedService {

    private final UseWordRepository useWordRepository;
    private final AnswerQuizSolvedRepository answerQuizSolvedRepository;
    private final S3Uploader s3Uploader;

    private static final String RECORD_BASE_FOLDER = "record/";


    /**
     * AnswerQuiz를 풀었을 때 AnswerQuizSolved로 푼 정보들을 저장
     *
     * @param answerQuiz      푼 문제의 정보
     * @param submitAnswer    사용자가 제출한 정답
     * @param member          푼 사용자
     * @param openApiResponse OpenApi를 이용해 분석한 결과
     * @param multipartFile   정답 녹음 파일
     */
    public void enrollNewAnswerQuizSolved(AnswerQuiz answerQuiz, String submitAnswer, Member member,
                                          OpenApiResponse openApiResponse, MultipartFile multipartFile) {

        AnswerQuizSolved answerQuizSolved = AnswerQuizSolved.builder()
                .answerQuiz(answerQuiz)
                .sentenceAnswer(submitAnswer)
                .recordAnswer(uploadRecordFile(member.getMemberId(), multipartFile))
                .member(member)
                .build();

        answerQuizSolvedRepository.save(answerQuizSolved);

        //아래에 있는 부분들 refactoring 시 bulk insertion 찾아보기
        enrollUseWords(openApiResponse, answerQuizSolved, member);
    }

    private void enrollUseWords(OpenApiResponse openApiResponse, AnswerQuizSolved answerQuizSolved, Member member) {
        // 회원의 기존 단어를 한 번에 조회해 단어명 -> 엔티티 맵으로 보관한다 (단어별 추가 조회 제거).
        Map<String, UseWord> existingWords = useWordRepository.findAllByMember(member).stream()
                .collect(Collectors.toMap(UseWord::getWordName, word -> word));

        List<UseWord> newUseWords = new ArrayList<>();

        openApiResponse.useWordVOList().forEach(useWordVO -> {
            String wordName = useWordVO.word();
            long count = useWordVO.count();

            UseWord useWord = existingWords.get(wordName);
            if (useWord != null) {
                // 영속 상태 엔티티이므로 dirty checking으로 반영된다.
                useWord.addCount(count);
            } else {
                UseWord newWord = buildUseWord(answerQuizSolved, member, wordName, count);
                // 동일 응답 내에 같은 단어가 또 나오면 새로 만들지 않고 누적되도록 맵에 등록한다.
                existingWords.put(wordName, newWord);
                newUseWords.add(newWord);
            }
        });

        useWordRepository.saveAll(newUseWords);
    }

    private static UseWord buildUseWord(AnswerQuizSolved answerQuizSolved, Member member, String wordName, long count) {
        return UseWord.builder()
                .wordName(wordName)
                .count(count)
                .answerQuizSolved(answerQuizSolved)
                .member(member)
                .build();
    }

    private S3Info uploadRecordFile(long memberId, MultipartFile multipartFile) {
        String folderName = RECORD_BASE_FOLDER + memberId;
        return s3Uploader.upload(multipartFile, folderName);
    }
}
