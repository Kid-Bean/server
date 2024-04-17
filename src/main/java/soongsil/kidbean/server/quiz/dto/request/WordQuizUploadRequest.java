package soongsil.kidbean.server.quiz.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quiz.domain.Word;
import soongsil.kidbean.server.quiz.domain.WordQuiz;
import soongsil.kidbean.server.quiz.domain.type.Level;

import java.util.ArrayList;
import java.util.List;

public record WordQuizUploadRequest(
        @NotNull(message = "제목을 입력해주세요.")
        String title,
        @NotNull(message = "정답을 입력해주세요.")
        String answer,
        @Size(min = 4, max = 4, message = "4개의 단어를 입력해주세요")
        List<WordRequest> words
) {
    public WordQuiz toWordQuiz(Member member) {
        WordQuiz wordQuiz = WordQuiz.builder()
                .title(title)
                .answer(answer)
                .words(new ArrayList<>())
                .level(Level.BRONZE)
                .member(member)
                .build();

        // 단어 목록을 Word 객체로 변환하여 WordQuiz 객체에 추가
        List<Word> wordList = words.stream()
                .map(wordRequest -> Word.builder()
                        .content(wordRequest.content())
                        .wordQuiz(wordQuiz)
                        .build())
                .toList();

        wordQuiz.getWords().addAll(wordList);

        return wordQuiz;
    }
}
