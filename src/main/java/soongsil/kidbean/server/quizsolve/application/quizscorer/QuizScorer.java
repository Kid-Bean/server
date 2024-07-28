package soongsil.kidbean.server.quizsolve.application.quizscorer;

import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quizsolve.application.quizsolver.dto.SolvedQuizInfo;

public  interface QuizScorer {
    Long addPerQuizScore(SolvedQuizInfo solvedQuizInfo, Member member);
}
