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
import soongsil.kidbean.server.quiz.application.vo.QuizType;
import soongsil.kidbean.server.quiz.domain.ImageQuiz;
import soongsil.kidbean.server.quiz.domain.QuizSolved;
import soongsil.kidbean.server.quiz.domain.type.Level;
import soongsil.kidbean.server.quiz.domain.type.QuizCategory;
import soongsil.kidbean.server.quiz.repository.QuizSolvedRepository;
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
    public void updateImageQuizLevel() {
        Map<Boolean, List<QuizSolved>> groupedByQuizType = quizSolvedRepository.findAll()
                .stream()
                .filter(QuizSolved::isImageQuizMadeByAdmin)
                .collect(Collectors.partitioningBy(QuizSolved::isImageQuiz));

        groupedByQuizType.forEach((isImageQuiz, quizSolvedList) -> {
            Map<ImageQuiz, List<QuizSolved>> groupByQuiz = quizSolvedList.stream()
                    .collect(Collectors.groupingBy(QuizSolved::getImageQuiz));

            groupByQuiz.forEach((quiz, solvedList) -> {
                Long accuracy = calculateAccuracy(solvedList);
                Level level = Level.calculate(accuracy);

                QuizType quizType = isImageQuiz ? QuizType.IMAGE_QUIZ : QuizType.WORD_QUIZ;

                if (quiz.isLevelUpdateNeed(level)) {
                    updateMemberTotalScore(quiz, level, quizType);
                    updateLevel(quiz, level);
                }
            });
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
        return members.stream()
                .flatMap(member -> quizSolvedRepository
                        .findAllByMemberAndIsCorrectTrue(member)
                        .stream())
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
                .stream()
                .count();
    }

    @Transactional
    public void updateMemberTotalScore(ImageQuiz imageQuiz, Level level, QuizType quizType) {
        List<QuizSolved> quizSolvedList = quizSolvedRepository.findAllByImageQuizAndIsCorrectTrue(imageQuiz);

        Set<Member> memberList = quizSolvedList.stream()
                .map(QuizSolved::getMember)
                .collect(Collectors.toSet());

        memberList.forEach(member -> {
            member.updateScore(imageQuiz.getLevel(), level);
            quizScoreRepository.findByMemberAndQuizCategory(member, imageQuiz.getQuizCategory())
                    .ifPresent(quizScore -> quizScore.updateScore(imageQuiz.getLevel(), level));
        });
    }

    @Transactional
    public void updateLevel(ImageQuiz imageQuiz, Level level) {
        imageQuiz.updateLevel(level);
    }

    private Long calculateAccuracy(List<QuizSolved> quizSolvedList) {
        long quizSolvedCount = quizSolvedList.size();

        if (quizSolvedCount > 0) {
            long correctSolved = quizSolvedList.stream()
                    .filter(QuizSolved::getIsCorrect).count();
            return (correctSolved / quizSolvedCount) * 100;
        }

        return null;
    }
}
