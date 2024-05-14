package soongsil.kidbean.server.program.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.exception.MemberNotFoundException;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.program.domain.Program;
import soongsil.kidbean.server.program.domain.Star;
import soongsil.kidbean.server.program.dto.response.StarResponse;
import soongsil.kidbean.server.program.exception.ProgramNotFoundException;
import soongsil.kidbean.server.program.repository.ProgramRepository;
import soongsil.kidbean.server.program.repository.StarRepository;


import static soongsil.kidbean.server.member.exception.errorcode.MemberErrorCode.MEMBER_NOT_FOUND;
import static soongsil.kidbean.server.program.exception.errorcode.ProgramErrorCode.PROGRAM_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class StarService {

    private final StarRepository starRepository;
    private final ProgramRepository programRepository;
    private final MemberRepository memberRepository;

    //즐겨 찾기 저장
    public StarResponse saveStar(Long memberId, Long programId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new ProgramNotFoundException(PROGRAM_NOT_FOUND));

        return starRepository.findByMemberAndProgram(member, program)
                .map(star -> {
                    starRepository.delete(star);
                    return StarResponse.from("delete");
                })
                .orElseGet(() -> {
                    starRepository.save(Star.from(member, program));
                    return StarResponse.from("save");
                });
    }

    @Transactional(readOnly = true)
    public Boolean existsByMemberAndProgram(Long memberId, Program program) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));

        return starRepository.existsByMemberAndProgram(member, program);
    }
}
