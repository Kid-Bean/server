package soongsil.kidbean.server.quiz.application;

import ch.qos.logback.core.testUtil.RandomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.quiz.domain.ImageQuiz;
import soongsil.kidbean.server.quiz.domain.type.Category;
import soongsil.kidbean.server.quiz.dto.response.ImageQuizMemberDetailResponse;
import soongsil.kidbean.server.quiz.dto.response.ImageQuizResponse;
import soongsil.kidbean.server.quiz.exception.ImageQuizNotFoundException;
import soongsil.kidbean.server.quiz.repository.ImageQuizRepository;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageQuizService {

    private final ImageQuizRepository imageQuizRepository;
    private final MemberRepository memberRepository;

    public ImageQuizMemberDetailResponse getImageQuizById(Long memberId, Long quizId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(RuntimeException::new);
        ImageQuiz imageQuiz = imageQuizRepository.findByMemberAndQuizId(member, quizId)
                .orElseThrow(RuntimeException::new);

        return ImageQuizMemberDetailResponse.from(imageQuiz);
    }

    //해당 카테고리에서 문제를 랜덤으로 선택
    public ImageQuizResponse selectRandomProblem() {
        Category category = selectRandomCategory();
        Page<ImageQuiz> imageQuizPage = generateRandomPageWithCategory(category);

        if (imageQuizPage.hasContent()) {
            return ImageQuizResponse.from(imageQuizPage.getContent().get(0));
        } else {
            throw new ImageQuizNotFoundException();
        }
    }

    private Page<ImageQuiz> generateRandomPageWithCategory(Category category) {
        int idx = RandomUtil.getPositiveInt() % imageQuizRepository.countByCategory(category);
        return imageQuizRepository.findAllByCategory(category, PageRequest.of(idx, 1));
    }

    private Category selectRandomCategory() {
        return Category.valueOfCode(RandomUtil.getPositiveInt() % 4);
    }
}
