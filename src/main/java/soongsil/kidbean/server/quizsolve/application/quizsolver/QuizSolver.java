package soongsil.kidbean.server.quizsolve.application.quizsolver;

import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quizsolve.application.quizsolver.dto.SolvedQuizInfo;
import soongsil.kidbean.server.quizsolve.dto.request.QuizSolvedRequest;

public interface QuizSolver {

    SolvedQuizInfo solveQuiz(QuizSolvedRequest solvedRequest, Member member);
}
