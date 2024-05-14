package soongsil.kidbean.server.program.repository.init;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import soongsil.kidbean.server.global.util.LocalDummyDataInit;
import soongsil.kidbean.server.program.domain.Day;
import soongsil.kidbean.server.program.domain.type.Date;
import soongsil.kidbean.server.program.repository.DayRepository;

@Slf4j
@RequiredArgsConstructor
@Order(3)
@LocalDummyDataInit
public class DayInitializer implements ApplicationRunner {

    private final DayRepository dayRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (dayRepository.count() > 0) {
            log.info("[Day]더미 데이터 존재");
        } else {
            List<Day> dayList = new ArrayList<>();

            Day program1_day1 = Day.builder()
                    .date(Date.MONDAY)
                    .program(ProgramInitializer.PROGRAM1)
                    .build();
            Day program1_day2 = Day.builder()
                    .date(Date.TUESDAY)
                    .program(ProgramInitializer.PROGRAM1)
                    .build();
            Day program1_day3 = Day.builder()
                    .date(Date.WEDNESDAY)
                    .program(ProgramInitializer.PROGRAM1)
                    .build();

            Day program2_day1 = Day.builder()
                    .date(Date.MONDAY)
                    .program(ProgramInitializer.PROGRAM2)
                    .build();
            Day program2_day3 = Day.builder()
                    .date(Date.WEDNESDAY)
                    .program(ProgramInitializer.PROGRAM2)
                    .build();
            Day program2_day4 = Day.builder()
                    .date(Date.THURSDAY)
                    .program(ProgramInitializer.PROGRAM2)
                    .build();

            Day program3_day2 = Day.builder()
                    .date(Date.TUESDAY)
                    .program(ProgramInitializer.PROGRAM3)
                    .build();
            Day program3_day5 = Day.builder()
                    .date(Date.FRIDAY)
                    .program(ProgramInitializer.PROGRAM3)
                    .build();
            Day program3_day7 = Day.builder()
                    .date(Date.SUNDAY)
                    .program(ProgramInitializer.PROGRAM3)
                    .build();

            Day program4_day2 = Day.builder()
                    .date(Date.TUESDAY)
                    .program(ProgramInitializer.PROGRAM4)
                    .build();
            Day program4_day3 = Day.builder()
                    .date(Date.WEDNESDAY)
                    .program(ProgramInitializer.PROGRAM4)
                    .build();
            Day program4_day4 = Day.builder()
                    .date(Date.THURSDAY)
                    .program(ProgramInitializer.PROGRAM4)
                    .build();
            Day program4_day5 = Day.builder()
                    .date(Date.FRIDAY)
                    .program(ProgramInitializer.PROGRAM4)
                    .build();

            Day program5_day1 = Day.builder()
                    .date(Date.MONDAY)
                    .program(ProgramInitializer.PROGRAM5)
                    .build();
            Day program5_day2 = Day.builder()
                    .date(Date.TUESDAY)
                    .program(ProgramInitializer.PROGRAM5)
                    .build();

            Day program6_day6 = Day.builder()
                    .date(Date.SATURDAY)
                    .program(ProgramInitializer.PROGRAM6)
                    .build();

            dayList.add(program1_day1);
            dayList.add(program1_day2);
            dayList.add(program1_day3);
            dayList.add(program2_day1);
            dayList.add(program2_day3);
            dayList.add(program2_day4);
            dayList.add(program3_day2);
            dayList.add(program3_day5);
            dayList.add(program3_day7);
            dayList.add(program4_day2);
            dayList.add(program4_day3);
            dayList.add(program4_day4);
            dayList.add(program4_day5);
            dayList.add(program5_day1);
            dayList.add(program5_day2);
            dayList.add(program6_day6);

            dayRepository.saveAll(dayList);
        }
    }
}
