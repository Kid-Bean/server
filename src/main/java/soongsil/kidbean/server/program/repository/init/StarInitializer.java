package soongsil.kidbean.server.program.repository.init;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import soongsil.kidbean.server.global.util.LocalDummyDataInit;
import soongsil.kidbean.server.member.repository.init.MemberInitializer;
import soongsil.kidbean.server.program.domain.Star;
import soongsil.kidbean.server.program.repository.StarRepository;

@Slf4j
@RequiredArgsConstructor
@Order(3)
@LocalDummyDataInit
public class StarInitializer implements ApplicationRunner {

    private final StarRepository starRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (starRepository.count() > 0) {
            log.info("[Star]더미 데이터 존재");
        } else {
            List<Star> starList = new ArrayList<>();

            Star program1_star1 = Star.builder()
                    .program(ProgramInitializer.PROGRAM1)
                    .member(MemberInitializer.DUMMY_MEMBER)
                    .build();
            Star program1_star2 = Star.builder()
                    .program(ProgramInitializer.PROGRAM1)
                    .member(MemberInitializer.DUMMY_ADMIN)
                    .build();

            Star program2_star1 = Star.builder()
                    .program(ProgramInitializer.PROGRAM2)
                    .member(MemberInitializer.DUMMY_MEMBER)
                    .build();
            Star program2_star2 = Star.builder()
                    .program(ProgramInitializer.PROGRAM2)
                    .member(MemberInitializer.DUMMY_ADMIN)
                    .build();

            Star program3_star1 = Star.builder()
                    .program(ProgramInitializer.PROGRAM3)
                    .member(MemberInitializer.DUMMY_MEMBER)
                    .build();
            Star program3_star2 = Star.builder()
                    .program(ProgramInitializer.PROGRAM3)
                    .member(MemberInitializer.DUMMY_ADMIN)
                    .build();

            Star program4_star1 = Star.builder()
                    .program(ProgramInitializer.PROGRAM4)
                    .member(MemberInitializer.DUMMY_MEMBER)
                    .build();
            Star program4_star2 = Star.builder()
                    .program(ProgramInitializer.PROGRAM4)
                    .member(MemberInitializer.DUMMY_ADMIN)
                    .build();

            Star program5_star1 = Star.builder()
                    .program(ProgramInitializer.PROGRAM5)
                    .member(MemberInitializer.DUMMY_MEMBER)
                    .build();
            Star program5_star2 = Star.builder()
                    .program(ProgramInitializer.PROGRAM5)
                    .member(MemberInitializer.DUMMY_ADMIN)
                    .build();

            Star program6_star1 = Star.builder()
                    .program(ProgramInitializer.PROGRAM6)
                    .member(MemberInitializer.DUMMY_MEMBER)
                    .build();
            Star program6_star2 = Star.builder()
                    .program(ProgramInitializer.PROGRAM6)
                    .member(MemberInitializer.DUMMY_ADMIN)
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
