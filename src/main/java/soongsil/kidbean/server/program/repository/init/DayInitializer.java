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
import soongsil.kidbean.server.program.domain.Program;
import soongsil.kidbean.server.program.domain.type.Date;
import soongsil.kidbean.server.program.repository.DayRepository;
import soongsil.kidbean.server.program.repository.ProgramRepository;

@Slf4j
@RequiredArgsConstructor
@Order(3)
@LocalDummyDataInit
public class DayInitializer implements ApplicationRunner {

    private final ProgramRepository programRepository;
    private final DayRepository dayRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (dayRepository.count() > 0) {
            log.info("[Day]더미 데이터 존재");
        } else {
            Program PROGRAM1 = programRepository.findById(1L).orElseThrow();
            Program PROGRAM2 = programRepository.findById(2L).orElseThrow();
            Program PROGRAM3 = programRepository.findById(3L).orElseThrow();
            Program PROGRAM4 = programRepository.findById(4L).orElseThrow();
            Program PROGRAM5 = programRepository.findById(5L).orElseThrow();
            Program PROGRAM6 = programRepository.findById(6L).orElseThrow();

            List<OpenDay> openDayList = new ArrayList<>();

            OpenDay program1_OpenDay1 = OpenDay.builder()
                    .date(Date.MONDAY)
                    .program(PROGRAM1)
                    .build();
            OpenDay program1_OpenDay2 = OpenDay.builder()
                    .date(Date.TUESDAY)
                    .program(PROGRAM1)
                    .build();
            OpenDay program1_OpenDay3 = OpenDay.builder()
                    .date(Date.WEDNESDAY)
                    .program(PROGRAM1)
                    .build();

            OpenDay program2_OpenDay1 = OpenDay.builder()
                    .date(Date.MONDAY)
                    .program(PROGRAM2)
                    .build();
            OpenDay program2_OpenDay3 = OpenDay.builder()
                    .date(Date.WEDNESDAY)
                    .program(PROGRAM2)
                    .build();
            OpenDay program2_OpenDay4 = OpenDay.builder()
                    .date(Date.THURSDAY)
                    .program(PROGRAM2)
                    .build();

            OpenDay program3_OpenDay2 = OpenDay.builder()
                    .date(Date.TUESDAY)
                    .program(PROGRAM3)
                    .build();
            OpenDay program3_OpenDay5 = OpenDay.builder()
                    .date(Date.FRIDAY)
                    .program(PROGRAM3)
                    .build();
            OpenDay program3_OpenDay7 = OpenDay.builder()
                    .date(Date.SUNDAY)
                    .program(PROGRAM3)
                    .build();

            OpenDay program4_OpenDay2 = OpenDay.builder()
                    .date(Date.TUESDAY)
                    .program(PROGRAM4)
                    .build();
            OpenDay program4_OpenDay3 = OpenDay.builder()
                    .date(Date.WEDNESDAY)
                    .program(PROGRAM4)
                    .build();
            OpenDay program4_OpenDay4 = OpenDay.builder()
                    .date(Date.THURSDAY)
                    .program(PROGRAM4)
                    .build();
            OpenDay program4_OpenDay5 = OpenDay.builder()
                    .date(Date.FRIDAY)
                    .program(PROGRAM4)
                    .build();

            OpenDay program5_OpenDay1 = OpenDay.builder()
                    .date(Date.MONDAY)
                    .program(PROGRAM5)
                    .build();
            OpenDay program5_OpenDay2 = OpenDay.builder()
                    .date(Date.TUESDAY)
                    .program(PROGRAM5)
                    .build();

            OpenDay program6_OpenDay6 = OpenDay.builder()
                    .date(Date.SATURDAY)
                    .program(PROGRAM6)
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

            dayRepository.saveAll(openDayList);
        }
    }
}
