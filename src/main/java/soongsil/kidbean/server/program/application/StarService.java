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
import soongsil.kidbean.server.program.repository.ProgramRepository;
import soongsil.kidbean.server.program.repository.StarRepository;

import java.util.List;

import static soongsil.kidbean.server.member.exception.errorcode.MemberErrorCode.MEMBER_NOT_FOUND;

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
                .orElseThrow(RuntimeException::new);

        if(!starRepository.existsByMemberAndProgram(starMember, starProgram)){
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
                .orElseThrow(()-> new MemberNotFoundException(MEMBER_NOT_FOUND));
        Program program = programRepository.findById(programId)
                .orElseThrow(RuntimeException::new);

        //repository optional 처리 -> null checking
        Star star = starRepository.findByMemberAndProgram(member, program)
                        .orElseThrow(()-> new MemberNotFoundException(MEMBER_NOT_FOUND));
        //예외전환

        starRepository.delete(star);
    }

    //즐겨찾기 조회
    public List<StarResponse> getStars(Long memberId, Long programId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(()-> new MemberNotFoundException(MEMBER_NOT_FOUND));
        Program program = programRepository.findById(programId)
                .orElseThrow(RuntimeException::new);

        return starRepository.findAllByMemberAndProgram(member, program).stream()
                .map(star -> new StarResponse(star.getStarId()))
                .toList();
    }
}
