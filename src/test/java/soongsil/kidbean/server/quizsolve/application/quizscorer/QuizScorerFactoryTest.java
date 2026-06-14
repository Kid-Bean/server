package soongsil.kidbean.server.quizsolve.application.quizscorer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import soongsil.kidbean.server.quizsolve.application.vo.QuizType;

class QuizScorerFactoryTest {

    private final ImageQuizScorer imageQuizScorer = mock(ImageQuizScorer.class);
    private final WordQuizScorer wordQuizScorer = mock(WordQuizScorer.class);
    private final QuizScorerFactory factory = new QuizScorerFactory(imageQuizScorer, wordQuizScorer);

    @Test
    @DisplayName("IMAGE_QUIZ 는 ImageQuizScorer 를 반환한다")
    void getImageScorer() {
        QuizScorer scorer = factory.getScorer(QuizType.IMAGE_QUIZ);

        assertThat(scorer).isSameAs(imageQuizScorer);
    }

    @Test
    @DisplayName("WORD_QUIZ 는 WordQuizScorer 를 반환한다")
    void getWordScorer() {
        QuizScorer scorer = factory.getScorer(QuizType.WORD_QUIZ);

        assertThat(scorer).isSameAs(wordQuizScorer);
    }
}
