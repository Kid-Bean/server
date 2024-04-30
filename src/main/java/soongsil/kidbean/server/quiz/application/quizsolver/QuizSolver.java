package soongsil.kidbean.server.quiz.application.quizsolver;

import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quiz.application.quizsolver.dto.SolvedQuizInfo;
import soongsil.kidbean.server.quiz.dto.request.QuizSolvedRequest;

public interface QuizSolver {

    SolvedQuizInfo solveQuiz(QuizSolvedRequest solvedRequest, Member member);
}
