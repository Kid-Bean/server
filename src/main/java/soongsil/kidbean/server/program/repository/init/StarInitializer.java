package soongsil.kidbean.server.program.repository.init;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import soongsil.kidbean.server.global.util.LocalDummyDataInit;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.program.domain.Program;
import soongsil.kidbean.server.program.domain.Star;
import soongsil.kidbean.server.program.repository.ProgramRepository;
import soongsil.kidbean.server.program.repository.StarRepository;

@Slf4j
@RequiredArgsConstructor
@Order(3)
@LocalDummyDataInit
public class StarInitializer implements ApplicationRunner {

    private final MemberRepository memberRepository;
    private final ProgramRepository programRepository;
    private final StarRepository starRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (starRepository.count() > 0) {
            log.info("[Star]더미 데이터 존재");
        } else {
            Member DUMMY_MEMBER = memberRepository.findBySocialId("socialId1").orElseThrow();
            Member DUMMY_ADMIN = memberRepository.findBySocialId("socialId2").orElseThrow();

            Program PROGRAM1 = programRepository.findById(1L).orElseThrow();
            Program PROGRAM2 = programRepository.findById(2L).orElseThrow();
            Program PROGRAM3 = programRepository.findById(3L).orElseThrow();
            Program PROGRAM4 = programRepository.findById(4L).orElseThrow();
            Program PROGRAM5 = programRepository.findById(5L).orElseThrow();
            Program PROGRAM6 = programRepository.findById(6L).orElseThrow();

            List<Star> starList = new ArrayList<>();

            Star program1_star1 = Star.builder()
                    .program(PROGRAM1)
                    .member(DUMMY_MEMBER)
                    .build();
            Star program1_star2 = Star.builder()
                    .program(PROGRAM1)
                    .member(DUMMY_ADMIN)
                    .build();

            Star program2_star1 = Star.builder()
                    .program(PROGRAM2)
                    .member(DUMMY_MEMBER)
                    .build();
            Star program2_star2 = Star.builder()
                    .program(PROGRAM2)
                    .member(DUMMY_ADMIN)
                    .build();

            Star program3_star1 = Star.builder()
                    .program(PROGRAM3)
                    .member(DUMMY_MEMBER)
                    .build();
            Star program3_star2 = Star.builder()
                    .program(PROGRAM3)
                    .member(DUMMY_ADMIN)
                    .build();

            Star program4_star1 = Star.builder()
                    .program(PROGRAM4)
                    .member(DUMMY_MEMBER)
                    .build();
            Star program4_star2 = Star.builder()
                    .program(PROGRAM4)
                    .member(DUMMY_ADMIN)
                    .build();

            Star program5_star1 = Star.builder()
                    .program(PROGRAM5)
                    .member(DUMMY_MEMBER)
                    .build();
            Star program5_star2 = Star.builder()
                    .program(PROGRAM5)
                    .member(DUMMY_ADMIN)
                    .build();

            Star program6_star1 = Star.builder()
                    .program(PROGRAM6)
                    .member(DUMMY_MEMBER)
                    .build();
            Star program6_star2 = Star.builder()
                    .program(PROGRAM6)
                    .member(DUMMY_ADMIN)
                    .build();

            starList.add(program1_star1);
            starList.add(program1_star2);
            starList.add(program2_star1);
            starList.add(program2_star2);
            starList.add(program3_star1);
            starList.add(program3_star2);
            starList.add(program4_star1);
            starList.add(program4_star2);
            starList.add(program5_star1);
            starList.add(program5_star2);
            starList.add(program6_star1);
            starList.add(program6_star2);

            starRepository.saveAll(starList);
        }
    }
}
