package soongsil.kidbean.server.program.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import soongsil.kidbean.server.global.vo.ImageInfo;
import soongsil.kidbean.server.program.domain.Program;
import soongsil.kidbean.server.program.repository.ProgramRepository;

import java.util.ArrayList;
import java.util.List;

import static soongsil.kidbean.server.program.domain.type.ProgramCategory.ACADEMY;
import static soongsil.kidbean.server.program.domain.type.ProgramCategory.HOSPITAL;

@Slf4j
@RequiredArgsConstructor
@Profile("dev")
@Component
public class ProgramInitializer implements ApplicationRunner {

    private final ProgramRepository programRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (programRepository.count() > 0) {
            log.info("[Program]더미 데이터 존재");
        } else {
            List<Program> programList = new ArrayList<>();

            programList.add(Program.builder()
                    .place("숭실대")
                    .title("숭실")
                    .phoneNumber("010-1234-1234")
                    .content("숭실")
                    .programCategory(ACADEMY)
                    .programImageInfo(new ImageInfo("imageURL" , "fileName", "folderName"))
                    .teacherName("숭실")
                    .teacherImageInfo(new ImageInfo("imageURL" , "fileName", "folderName"))
                    .build());
            programList.add(Program.builder()
                    .place("숭실대")
                    .title("숭실")
                    .phoneNumber("010-1234-1234")
                    .content("숭실")
                    .programCategory(HOSPITAL)
                    .programImageInfo(new ImageInfo("imageURL" , "fileName", "folderName"))
                    .teacherName("숭실")
                    .teacherImageInfo(new ImageInfo("imageURL" , "fileName", "folderName"))
                    .build());
            programList.add(Program.builder()
                    .place("숭실대")
                    .title("숭실")
                    .phoneNumber("010-1234-1234")
                    .content("숭실")
                    .programCategory(ACADEMY)
                    .programImageInfo(new ImageInfo("imageURL" , "fileName", "folderName"))
                    .teacherName("숭실")
                    .teacherImageInfo(new ImageInfo("imageURL" , "fileName", "folderName"))
                    .build());
            programList.add(Program.builder()
                    .place("숭실대")
                    .title("숭실")
                    .phoneNumber("010-1234-1234")
                    .content("숭실")
                    .programCategory(HOSPITAL)
                    .programImageInfo(new ImageInfo("imageURL" , "fileName", "folderName"))
                    .teacherName("숭실")
                    .teacherImageInfo(new ImageInfo("imageURL" , "fileName", "folderName"))
                    .build());
            programList.add(Program.builder()
                    .place("숭실대")
                    .title("숭실")
                    .phoneNumber("010-1234-1234")
                    .content("숭실")
                    .programCategory(ACADEMY)
                    .programImageInfo(new ImageInfo("imageURL" , "fileName", "folderName"))
                    .teacherName("숭실")
                    .teacherImageInfo(new ImageInfo("imageURL" , "fileName", "folderName"))
                    .build());
            programList.add(Program.builder()
                    .place("숭실대")
                    .title("숭실")
                    .phoneNumber("010-1234-1234")
                    .content("숭실")
                    .programCategory(ACADEMY)
                    .programImageInfo(new ImageInfo("imageURL" , "fileName", "folderName"))
                    .teacherName("숭실")
                    .teacherImageInfo(new ImageInfo("imageURL" , "fileName", "folderName"))
                    .build());

            programRepository.saveAll(programList);
        }
    }
}
