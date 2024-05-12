package soongsil.kidbean.server.summary.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.domain.type.Role;
import soongsil.kidbean.server.member.exception.MemberNotFoundException;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.quiz.domain.QuizSolved;
import soongsil.kidbean.server.quiz.domain.type.Level;
import soongsil.kidbean.server.quiz.domain.type.QuizCategory;
import soongsil.kidbean.server.quiz.repository.QuizSolvedRepository;
import soongsil.kidbean.server.summary.domain.AverageScore;
import soongsil.kidbean.server.summary.domain.QuizScore;
import soongsil.kidbean.server.summary.domain.type.AgeGroup;
import soongsil.kidbean.server.summary.repository.AverageScoreRepository;
import soongsil.kidbean.server.summary.repository.QuizScoreRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static soongsil.kidbean.server.member.exception.errorcode.MemberErrorCode.MEMBER_NOT_FOUND;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class scoreService {

    private final MemberRepository memberRepository;
    private final QuizScoreRepository quizScoreRepository;
    private final QuizSolvedRepository quizSolvedRepository;

    //특정 카테고리에 해당하는 퀴즈만 가져오기
    private List<QuizSolved> filterSolvedListByCategory(List<QuizSolved> quizSolvedList, QuizCategory category) {
        return quizSolvedList.stream()
                .filter(quizSolved -> category == quizSolved.getQuizCategory())
                .toList();
    }

    private long calculateSum(List<QuizSolved> solvedList) {
        return solvedList.stream()
                .mapToLong(quizSolved -> Level.getPoint(quizSolved.getImageQuiz().getLevel()))
                .sum();
    }

    protected int calculateTotalAverageScore(Member member, QuizCategory category) {
        List<QuizSolved> solvedListForMember = quizSolvedRepository.findAllByMemberAndIsCorrectTrue(member);
        List<QuizSolved> solvedListForCategory = filterSolvedListByCategory(solvedListForMember, category);

        int sum = (int) calculateSum(solvedListForCategory);
        int count = solvedListForCategory.size();

        int averageScore = count > 0 ? sum / count : 0;

        return averageScore;
    }


    protected int calculateTotalAverageScore(QuizCategory category) {

        List<QuizScore> quizScoreOfCategory = quizScoreRepository.findAllByQuizCategory(category);

        int index = 0;
        int[] quizScoreOfCategoryArray = new int[quizScoreOfCategory.size()];
        for (QuizScore a : quizScoreOfCategory) {
            Object total = a.getTotalScore();
            quizScoreOfCategoryArray[index] = (int) total;
            index++;

        }

        int sum = 0;
        for (int score = 0; score < quizScoreOfCategoryArray.length; score++) {
            sum += quizScoreOfCategoryArray[score];
        }

        int score = sum / quizScoreOfCategoryArray.length;

        log.info("점수",score);
        return score;
    }
}


