package soongsil.kidbean.server.program.repository.init;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import soongsil.kidbean.server.global.util.LocalDummyDataInit;
import soongsil.kidbean.server.program.domain.OpenDay;
import soongsil.kidbean.server.program.domain.type.Date;
import soongsil.kidbean.server.program.repository.OpenDayRepository;

@Slf4j
@RequiredArgsConstructor
@Order(3)
@LocalDummyDataInit
public class OpenDayInitializer implements ApplicationRunner {

    private final OpenDayRepository openDayRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (openDayRepository.count() > 0) {
            log.info("[OpenDay]더미 데이터 존재");
        } else {
            List<OpenDay> openDayList = new ArrayList<>();

            OpenDay program1_OpenDay1 = OpenDay.builder()
                    .date(Date.MONDAY)
                    .program(ProgramInitializer.PROGRAM1)
                    .build();
            OpenDay program1_OpenDay2 = OpenDay.builder()
                    .date(Date.TUESDAY)
                    .program(ProgramInitializer.PROGRAM1)
                    .build();
            OpenDay program1_OpenDay3 = OpenDay.builder()
                    .date(Date.WEDNESDAY)
                    .program(ProgramInitializer.PROGRAM1)
                    .build();

            OpenDay program2_OpenDay1 = OpenDay.builder()
                    .date(Date.MONDAY)
                    .program(ProgramInitializer.PROGRAM2)
                    .build();
            OpenDay program2_OpenDay3 = OpenDay.builder()
                    .date(Date.WEDNESDAY)
                    .program(ProgramInitializer.PROGRAM2)
                    .build();
            OpenDay program2_OpenDay4 = OpenDay.builder()
                    .date(Date.THURSDAY)
                    .program(ProgramInitializer.PROGRAM2)
                    .build();

            OpenDay program3_OpenDay2 = OpenDay.builder()
                    .date(Date.TUESDAY)
                    .program(ProgramInitializer.PROGRAM3)
                    .build();
            OpenDay program3_OpenDay5 = OpenDay.builder()
                    .date(Date.FRIDAY)
                    .program(ProgramInitializer.PROGRAM3)
                    .build();
            OpenDay program3_OpenDay7 = OpenDay.builder()
                    .date(Date.SUNDAY)
                    .program(ProgramInitializer.PROGRAM3)
                    .build();

            OpenDay program4_OpenDay2 = OpenDay.builder()
                    .date(Date.TUESDAY)
                    .program(ProgramInitializer.PROGRAM4)
                    .build();
            OpenDay program4_OpenDay3 = OpenDay.builder()
                    .date(Date.WEDNESDAY)
                    .program(ProgramInitializer.PROGRAM4)
                    .build();
            OpenDay program4_OpenDay4 = OpenDay.builder()
                    .date(Date.THURSDAY)
                    .program(ProgramInitializer.PROGRAM4)
                    .build();
            OpenDay program4_OpenDay5 = OpenDay.builder()
                    .date(Date.FRIDAY)
                    .program(ProgramInitializer.PROGRAM4)
                    .build();

            OpenDay program5_OpenDay1 = OpenDay.builder()
                    .date(Date.MONDAY)
                    .program(ProgramInitializer.PROGRAM5)
                    .build();
            OpenDay program5_OpenDay2 = OpenDay.builder()
                    .date(Date.TUESDAY)
                    .program(ProgramInitializer.PROGRAM5)
                    .build();

            OpenDay program6_OpenDay6 = OpenDay.builder()
                    .date(Date.SATURDAY)
                    .program(ProgramInitializer.PROGRAM6)
                    .build();

            openDayList.add(program1_OpenDay1);
            openDayList.add(program1_OpenDay2);
            openDayList.add(program1_OpenDay3);
            openDayList.add(program2_OpenDay1);
            openDayList.add(program2_OpenDay3);
            openDayList.add(program2_OpenDay4);
            openDayList.add(program3_OpenDay2);
            openDayList.add(program3_OpenDay5);
            openDayList.add(program3_OpenDay7);
            openDayList.add(program4_OpenDay2);
            openDayList.add(program4_OpenDay3);
            openDayList.add(program4_OpenDay4);
            openDayList.add(program4_OpenDay5);
            openDayList.add(program5_OpenDay1);
            openDayList.add(program5_OpenDay2);
            openDayList.add(program6_OpenDay6);

            openDayRepository.saveAll(openDayList);
        }
    }
}
