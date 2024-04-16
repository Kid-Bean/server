package soongsil.kidbean.server.member.dto.response;


import java.util.List;
import soongsil.kidbean.server.quiz.domain.QuizSolved;
import soongsil.kidbean.server.quiz.domain.Word;
import soongsil.kidbean.server.quiz.domain.WordQuiz;

public record SolvedWordDetailResponse(
        Long solvedId,
        String title,
        List<String> wordList,
        String answer,
        String kidAnswer
) {
    public static SolvedWordDetailResponse from(QuizSolved quizSolved, List<Word> wordList) {
        WordQuiz wordQuiz = quizSolved.getWordQuiz();
        return new SolvedWordDetailResponse(
                quizSolved.getSolvedId(),
                wordQuiz.getTitle(),
                wordList.stream().map(Word::getContent).toList(),
                wordQuiz.getAnswer(),
                quizSolved.getReply()
        );
    }
}
