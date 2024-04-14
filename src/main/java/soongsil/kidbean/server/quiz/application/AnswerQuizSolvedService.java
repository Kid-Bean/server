package soongsil.kidbean.server.quiz.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import soongsil.kidbean.server.global.application.S3Uploader;
import soongsil.kidbean.server.global.vo.S3Info;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quiz.application.vo.OpenApiResponse;
import soongsil.kidbean.server.quiz.domain.AnswerQuiz;
import soongsil.kidbean.server.quiz.domain.AnswerQuizSolved;
import soongsil.kidbean.server.quiz.domain.Morpheme;
import soongsil.kidbean.server.quiz.domain.UseWord;
import soongsil.kidbean.server.quiz.repository.AnswerQuizSolvedRepository;
import soongsil.kidbean.server.quiz.repository.MorphemeRepository;
import soongsil.kidbean.server.quiz.repository.UseWordRepository;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AnswerQuizSolvedService {

    private final MorphemeRepository morphemeRepository;
    private final UseWordRepository useWordRepository;
    private final AnswerQuizSolvedRepository answerQuizSolvedRepository;
    private final S3Uploader s3Uploader;

    private final static String RECORD_BASE_FOLDER = "record/";


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
        enrollMorphemes(openApiResponse, answerQuizSolved);
        enrollUseWords(openApiResponse, answerQuizSolved);
    }

    private void enrollMorphemes(OpenApiResponse openApiResponse, AnswerQuizSolved answerQuizSolved) {
        List<Morpheme> morphemeList = openApiResponse.morphemeVOList().stream()
                .map(morphemeVO -> Morpheme.builder()
                        .morpheme(morphemeVO.lemma())
                        .type(morphemeVO.type())
                        .answerQuizSolved(answerQuizSolved)
                        .build())
                .toList();

        morphemeRepository.saveAll(morphemeList);
    }

    private void enrollUseWords(OpenApiResponse openApiResponse, AnswerQuizSolved answerQuizSolved) {
        List<UseWord> useWordList = openApiResponse.useWordVOList().stream()
                .map(useWordVO -> UseWord.builder()
                        .wordName(useWordVO.word())
                        .count(useWordVO.count())
                        .answerQuizSolved(answerQuizSolved)
                        .build())
                .toList();

        useWordRepository.saveAll(useWordList);
    }

    private S3Info uploadRecordFile(long memberId, MultipartFile multipartFile) {
        String folderName = RECORD_BASE_FOLDER + memberId;
        String uploadUrl = s3Uploader.upload(multipartFile, folderName);
        String fileName = uploadUrl.split(folderName + "/")[1];

        return S3Info.builder()
                .folderName(folderName)
                .fileName(fileName)
                .s3Url(uploadUrl)
                .build();
    }
}
