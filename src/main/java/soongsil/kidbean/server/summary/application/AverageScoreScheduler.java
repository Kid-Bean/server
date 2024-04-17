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
import soongsil.kidbean.server.quiz.domain.ImageQuiz;
import soongsil.kidbean.server.quiz.domain.QuizSolved;
import soongsil.kidbean.server.quiz.domain.type.Level;
import soongsil.kidbean.server.quiz.domain.type.QuizCategory;
import soongsil.kidbean.server.quiz.repository.QuizSolvedRepository;
import soongsil.kidbean.server.summary.domain.AverageScore;
import soongsil.kidbean.server.summary.domain.type.AgeGroup;
import soongsil.kidbean.server.summary.repository.AverageScoreRepository;
import soongsil.kidbean.server.summary.repository.ImageQuizScoreRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AverageScoreScheduler {

    private final QuizSolvedRepository quizSolvedRepository;
    private final MemberRepository memberRepository;
    private final ImageQuizScoreRepository imageQuizScoreRepository;
    private final AverageScoreRepository averageScoreRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void updateImageQuizLevel() {
        Map<ImageQuiz, List<QuizSolved>> groupByImageQuiz = quizSolvedRepository.findAllByImageQuizIsNotNull()
                .stream()
                .filter(QuizSolved::isImageQuizMadeByAdmin)
                .collect(
                        Collectors.groupingBy(QuizSolved::getImageQuiz)
                );

        groupByImageQuiz.forEach((imageQuiz, quizSolvedList) -> {
            Long accuracy = calculateAccuracy(quizSolvedList);

            Level level = Level.calculate(accuracy);

            if ((imageQuiz.getLevel() == null) || (level != imageQuiz.getLevel())) {
                updateMemberTotalScore(imageQuiz, level);
                imageQuiz.updateLevel(level);
            }
        });
    }

    @Scheduled(cron = "0 0 * * * *")
    public void updateAverageScore() {
        Map<AgeGroup, List<Member>> groupMember = memberRepository.findAllByRole(Role.MEMBER).stream()
                .filter(member -> member.getBirthDate() != null)
                .collect(Collectors.groupingBy(
                        member -> AgeGroup.calculate(member.getBirthDate())
                ));

        groupMember.forEach((ageGroup, members) -> {
            List<QuizSolved> quizSolvedList = members.stream()
                    .flatMap(member -> quizSolvedRepository
                            .findAllByMemberAndIsCorrectTrueAndImageQuizIsNotNull(member)
                            .stream())
                    .toList();

            List<QuizCategory> quizCategories = QuizCategory.allValue();

            for (QuizCategory category : quizCategories) {
                long sum = quizSolvedList.stream()
                        .filter(quizSolved -> category == quizSolved.getImageQuiz().getQuizCategory())
                        .mapToLong(quizSolved -> Level.getPoint(quizSolved.getImageQuiz().getLevel()))
                        .sum();

                Optional<AverageScore> optionalAverageScore = averageScoreRepository.findByAgeGroup(ageGroup);

                if (optionalAverageScore.isPresent()) {
                    AverageScore averageScore = optionalAverageScore.get();
                    averageScore.updateScoreAndCount(sum, quizSolvedList.size());
                } else {
                    averageScoreRepository.save(new AverageScore(ageGroup, sum, quizSolvedList.size(), category));
                }
            }
        });
    }

    @Transactional
    public void updateMemberTotalScore(ImageQuiz imageQuiz, Level level) {
        List<QuizSolved> quizSolvedList = quizSolvedRepository.findAllByImageQuizAndIsCorrectTrue(imageQuiz);

        Set<Member> memberList = quizSolvedList.stream()
                .map(QuizSolved::getMember)
                .collect(Collectors.toSet());

        memberList.forEach(member -> {
            member.updateScore(imageQuiz.getLevel(), level);
            imageQuizScoreRepository.findByMemberAndQuizCategory(member, imageQuiz.getQuizCategory())
                    .ifPresent(imageQuizScore -> imageQuizScore.updateScore(imageQuiz.getLevel(), level));
        });
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
