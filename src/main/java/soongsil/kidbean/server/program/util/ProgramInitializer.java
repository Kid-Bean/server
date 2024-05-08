package soongsil.kidbean.server.program.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import soongsil.kidbean.server.global.vo.S3Info;
import soongsil.kidbean.server.program.domain.Program;
import soongsil.kidbean.server.program.repository.ProgramRepository;
import soongsil.kidbean.server.quiz.domain.AnswerQuiz;

import java.util.ArrayList;
import java.util.List;

import static soongsil.kidbean.server.member.repository.init.MemberInitializer.DUMMY_MEMBER;
import static soongsil.kidbean.server.program.domain.type.ProgramCategory.ACADEMY;
import static soongsil.kidbean.server.program.domain.type.ProgramCategory.HOSPITAL;

@Slf4j
@RequiredArgsConstructor
@Profile("dev")
@Order(2)
@Component
public class ProgramInitializer implements ApplicationRunner {

    private final ProgramRepository programRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (programRepository.count() > 0) {
            log.info("[Program]더미 데이터 존재");
        } else {
            List<Program> programList = new ArrayList<>();

            Program program1= Program.builder()
                    .place("1번출구")
                    .title("1타강사")
                    .titleInfo("1번 입니다")
                    .phoneNumber("010-1234-1234")
                    .content("1번은 바로 1타강사")
                    .programCategory(ACADEMY)
                    .programImageInfo(new S3Info("imageURL", "fileName", "folderName"))
                    .teacherName("숭실인1")
                    .teacherImageInfo(new S3Info("imageURL", "fileName", "folderName"))
                    .member(DUMMY_MEMBER)
                    .build();
            programList.add(program1);

            Program program2= Program.builder()
                    .place("2번출구")
                    .title("2타강사")
                    .titleInfo("2번 입니다")
                    .phoneNumber("010-1234-1234")
                    .content("2번은 바로 2타강사")
                    .programCategory(ACADEMY)
                    .programImageInfo(new S3Info("imageURL", "fileName", "folderName"))
                    .teacherName("숭실인2")
                    .teacherImageInfo(new S3Info("imageURL", "fileName", "folderName"))
                    .member(DUMMY_MEMBER)
                    .build();
            programList.add(program2);


            Program program3= Program.builder()
                    .place("3번출구")
                    .title("3타강사")
                    .titleInfo("3번 입니다")
                    .phoneNumber("010-1234-1234")
                    .content("3번은 바로 3타강사")
                    .programCategory(ACADEMY)
                    .programImageInfo(new S3Info("imageURL", "fileName", "folderName"))
                    .teacherName("숭실인3")
                    .teacherImageInfo(new S3Info("imageURL", "fileName", "folderName"))
                    .member(DUMMY_MEMBER)
                    .build();
            programList.add(program3);

            Program program4= Program.builder()
                    .place("1번출구")
                    .title("1타의사")
                    .titleInfo("1번 입니다")
                    .phoneNumber("010-1234-1234")
                    .content("1번은 바로 1타의사")
                    .programCategory(HOSPITAL)
                    .programImageInfo(new S3Info("imageURL", "fileName", "folderName"))
                    .teacherName("숭실인4")
                    .teacherImageInfo(new S3Info("imageURL", "fileName", "folderName"))
                    .member(DUMMY_MEMBER)
                    .build();
            programList.add(program4);

            Program program5= Program.builder()
                    .place("2번출구")
                    .title("2타의사")
                    .titleInfo("2번 입니다")
                    .phoneNumber("010-1234-1234")
                    .content("2번은 바로 2타강사")
                    .programCategory(HOSPITAL)
                    .programImageInfo(new S3Info("imageURL", "fileName", "folderName"))
                    .teacherName("숭실인5")
                    .teacherImageInfo(new S3Info("imageURL", "fileName", "folderName"))
                    .member(DUMMY_MEMBER)
                    .build();
            programList.add(program5);

            Program program6= Program.builder()
                    .place("3번출구")
                    .title("3타의사")
                    .titleInfo("3번 입니다")
                    .phoneNumber("010-1234-1234")
                    .content("3번은 바로 3타의사")
                    .programCategory(HOSPITAL)
                    .programImageInfo(new S3Info("imageURL", "fileName", "folderName"))
                    .teacherName("숭실인6")
                    .teacherImageInfo(new S3Info("imageURL", "fileName", "folderName"))
                    .member(DUMMY_MEMBER)
                    .build();
            programList.add(program6);

            programRepository.saveAll(programList);
        }
    }
}
