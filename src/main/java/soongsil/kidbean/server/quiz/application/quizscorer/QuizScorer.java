package soongsil.kidbean.server.quiz.application.quizscorer;

import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quiz.application.quizsolver.dto.SolvedQuizInfo;

public  interface QuizScorer {
    Long addPerQuizScore(SolvedQuizInfo solvedQuizInfo, Member member);
}
