package soongsil.kidbean.server.quiz.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.quiz.repository.SentenceQuizRepository;
import soongsil.kidbean.server.quiz.repository.SentenceQuizWordRepository;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SentenceQuizService {

    private final SentenceQuizRepository sentenceQuizRepository;
    private final SentenceQuizWordRepository sentenceQuizWordRepository;
}
