package soongsil.kidbean.server.program.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.exception.MemberNotFoundException;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.program.domain.Program;
import soongsil.kidbean.server.program.domain.Star;
import soongsil.kidbean.server.program.dto.response.ProgramResponseList;
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

    //즐겨찾기 저장
    public void saveStar(Long memberId, Long programId) {
        Member starMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
        Program starProgram = programRepository.findById(programId)
                .orElseThrow(() -> new ProgramNotFoundException(PROGRAM_NOT_FOUND));

        if (!starRepository.existsByMemberAndProgram(starMember, starProgram)) {
            Star star = Star.builder()
                    .memberId(starMember)
                    .programId(starProgram)
                    .build();

            starRepository.save(star);
        }
    }

    // 즐겨찾기 삭제
    public void deleteStar(Long memberId, Long programId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new ProgramNotFoundException(PROGRAM_NOT_FOUND));

        //repository optional 처리 -> null checking
        Star star = starRepository.findByMemberAndProgram(member, program)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
        //예외전환

        starRepository.delete(star);
    }

    //즐겨 찾기 목록 조회
    public ProgramResponseList getStarProgramList(Long memberId, Long programId, Pageable pageable) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new ProgramNotFoundException(PROGRAM_NOT_FOUND));

        //starRepository 에서 member 가 즐겨 찾기한 프로그램 목록 pageable 로 가져오기
        //programRepository 에서 위에서 찾은 프로그램 목록을 가져 와서 return

        return null;
    }

    @Transactional(readOnly = true)
    public Boolean existsByMemberAndProgram(Long memberId, Program program) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));

        return starRepository.existsByMemberAndProgram(member, program);
    }
}
