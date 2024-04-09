package soongsil.kidbean.server.quiz.application.quizsolver;

import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.quiz.dto.request.QuizSolvedRequest;

public interface QuizSolver {

    Long solveQuiz(QuizSolvedRequest solvedRequest, Member member);
}
