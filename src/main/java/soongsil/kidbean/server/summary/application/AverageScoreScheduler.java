package soongsil.kidbean.server.summary.application;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.domain.type.Role;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.imagequiz.domain.ImageQuiz;
import soongsil.kidbean.server.quizsolve.domain.QuizSolved;
import soongsil.kidbean.server.quizsolve.domain.type.Level;
import soongsil.kidbean.server.quizsolve.domain.type.QuizCategory;
import soongsil.kidbean.server.quizsolve.repository.QuizSolvedRepository;
import soongsil.kidbean.server.summary.domain.AverageScore;
import soongsil.kidbean.server.summary.domain.type.AgeGroup;
import soongsil.kidbean.server.summary.repository.AverageScoreRepository;
import soongsil.kidbean.server.summary.repository.QuizScoreRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AverageScoreScheduler {

    private final QuizSolvedRepository quizSolvedRepository;
    private final MemberRepository memberRepository;
    private final QuizScoreRepository quizScoreRepository;
    private final AverageScoreRepository averageScoreRepository;

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void updateImageQuizLevel() {
        Map<ImageQuiz, List<QuizSolved>> solvedByQuiz = quizSolvedRepository.findAllAdminImageQuizSolved()
                .stream()
                .collect(Collectors.groupingBy(QuizSolved::getImageQuiz));

        solvedByQuiz.forEach((quiz, solvedList) -> {
            Long accuracy = calculateAccuracy(solvedList);
            Level level = Level.calculate(accuracy);

            if (quiz.isLevelUpdateNeed(level)) {
                // 점수 갱신은 기존(이전) 레벨 기준이므로 레벨 변경 전에 먼저 수행한다.
                updateMemberTotalScore(quiz, solvedList, level);
                quiz.updateLevel(level);
            }
        });
    }

    @Scheduled(cron = "0 59 * * * *")
    @Transactional
    public void updateAverageScore() {
        Map<AgeGroup, List<Member>> groupMember = getGroupedMembersByRole();

        groupMember.forEach((ageGroup, members) -> {
            List<QuizSolved> quizSolvedList = getDistinctQuizSolvedList(members);

            List<QuizCategory> quizCategories = QuizCategory.allValue();

            for (QuizCategory category : quizCategories) {
                List<QuizSolved> solvedList = filterSolvedListByCategory(quizSolvedList, category);

                long sum = calculateSum(solvedList);
                long memberCount = getDistinctMemberCount(solvedList);

                Optional<AverageScore> optionalAverageScore = averageScoreRepository.findByAgeGroupAndQuizCategory(
                        ageGroup, category);

                if (optionalAverageScore.isPresent()) {
                    AverageScore averageScore = optionalAverageScore.get();
                    averageScore.updateScoreAndCount(sum, memberCount);
                } else {
                    averageScoreRepository.save(new AverageScore(ageGroup, sum, memberCount, category));
                }
            }
        });
    }

    private Map<AgeGroup, List<Member>> getGroupedMembersByRole() {
        return memberRepository.findAllByRole(Role.MEMBER).stream()
                .filter(member -> member.getBirthDate() != null)
                .collect(Collectors.groupingBy(member -> AgeGroup.calculate(member.getBirthDate())));
    }

    private List<QuizSolved> getDistinctQuizSolvedList(List<Member> members) {
        if (members.isEmpty()) {
            return List.of();
        }
        return quizSolvedRepository.findAllCorrectByMembers(members).stream()
                .distinct()
                .toList();
    }

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

    private long getDistinctMemberCount(List<QuizSolved> solvedList) {
        return solvedList.stream()
                .collect(Collectors.groupingBy(QuizSolved::getMember))
                .keySet()
                .size();
    }

    private void updateMemberTotalScore(ImageQuiz imageQuiz, List<QuizSolved> solvedList, Level newLevel) {
        Set<Member> memberList = solvedList.stream()
                .filter(QuizSolved::getIsCorrect)
                .map(QuizSolved::getMember)
                .collect(Collectors.toSet());

        memberList.forEach(member -> {
            member.updateScore(imageQuiz.getLevel(), newLevel);
            quizScoreRepository.findByMemberAndQuizCategory(member, imageQuiz.getQuizCategory())
                    .ifPresent(quizScore -> quizScore.updateScore(imageQuiz.getLevel(), newLevel));
        });
    }

    private Long calculateAccuracy(List<QuizSolved> quizSolvedList) {
        long quizSolvedCount = quizSolvedList.size();

        if (quizSolvedCount > 0) {
            long correctSolved = quizSolvedList.stream()
                    .filter(QuizSolved::getIsCorrect).count();
            // 정수 나눗셈으로 항상 0/100 이 되던 버그 수정: 곱을 먼저 수행한다.
            return (correctSolved * 100) / quizSolvedCount;
        }

        return null;
    }
}
