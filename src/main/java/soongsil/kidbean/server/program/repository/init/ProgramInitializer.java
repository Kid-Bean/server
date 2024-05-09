package soongsil.kidbean.server.program.repository.init;

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

            Program program1 = Program.builder()
                    .place("1번출구")
                    .title("1타강사")
                    .contentTitle("1번 입니다")
                    .phoneNumber("010-1234-1234")
                    .content("1번은 바로 1타강사")
                    .programCategory(ACADEMY)
                    .teacherName("숭실인1")
                    .programS3Url(S3Info.builder()
                            .folderName("program/HOSPITAL")
                            .fileName("first.jpg")
                            .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/program/HOSPITAL/first.jpg")
                            .build()
                    )
                    .teacherS3Url(
                            S3Info.builder()
                                    .folderName("teacher/HOSPITAL")
                                    .fileName("first.jpg")
                                    .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/teacher/HOSPITAL/first.jpg")
                                    .build()
                    )
                    .member(DUMMY_MEMBER)
                    .build();
            programList.add(program1);

            Program program2 = Program.builder()
                    .place("2번출구")
                    .title("2타강사")
                    .contentTitle("2번 입니다")
                    .phoneNumber("010-1234-1234")
                    .content("2번은 바로 2타강사")
                    .programCategory(ACADEMY)
                    .teacherName("숭실인2")
                    .programS3Url(S3Info.builder()
                            .folderName("program/HOSPITAL")
                            .fileName("second.jpg")
                            .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/program/HOSPITAL/second.jpg")
                            .build()
                    )
                    .teacherS3Url(
                            S3Info.builder()
                                    .folderName("teacher/HOSPITAL")
                                    .fileName("second.jpg")
                                    .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/teacher/HOSPITAL/second.jpg")
                                    .build()
                    )
                    .member(DUMMY_MEMBER)
                    .build();
            programList.add(program2);


            Program program3 = Program.builder()
                    .place("3번출구")
                    .title("3타강사")
                    .contentTitle("3번 입니다")
                    .phoneNumber("010-1234-1234")
                    .content("3번은 바로 3타강사")
                    .programCategory(ACADEMY)
                    .teacherName("숭실인3")
                    .programS3Url(S3Info.builder()
                            .folderName("program/HOSPITAL")
                            .fileName("third.jpg")
                            .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com//program/HOSPITAL/third.jpg")
                            .build()
                    )
                    .teacherS3Url(
                            S3Info.builder()
                                    .folderName("teacher/HOSPITAL")
                                    .fileName("third.jpg")
                                    .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/teacher/HOSPITAL/third.jpg")
                                    .build()
                    )
                    .member(DUMMY_MEMBER)
                    .build();
            programList.add(program3);

            Program program4 = Program.builder()
                    .place("1번출구")
                    .title("1타의사")
                    .contentTitle("1번 입니다")
                    .phoneNumber("010-1234-1234")
                    .content("1번은 바로 1타의사")
                    .programCategory(HOSPITAL)
                    .teacherName("숭실인4")
                    .programS3Url(S3Info.builder()
                            .folderName("program/ACADEMY")
                            .fileName("first.jpg")
                            .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/program/ACADEMY/first.jpg")
                            .build()
                    )
                    .teacherS3Url(
                            S3Info.builder()
                                    .folderName("teacher/ACADEMY")
                                    .fileName("first.jpg")
                                    .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/teacher/ACADEMY/first.jpg")
                                    .build()
                    )
                    .member(DUMMY_MEMBER)
                    .build();
            programList.add(program4);

            Program program5 = Program.builder()
                    .place("2번출구")
                    .title("2타의사")
                    .contentTitle("2번 입니다")
                    .phoneNumber("010-1234-1234")
                    .content("2번은 바로 2타강사")
                    .programCategory(HOSPITAL)
                    .teacherName("숭실인5")
                    .programS3Url(S3Info.builder()
                            .folderName("program/ACADEMY")
                            .fileName("second.jpg")
                            .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/program/ACADEMY/second.jpg")
                            .build()
                    )
                    .teacherS3Url(
                            S3Info.builder()
                                    .folderName("teacher/ACADEMY")
                                    .fileName("second.jpg")
                                    .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/teacher/ACADEMY/second.jpg")
                                    .build()
                    )
                    .member(DUMMY_MEMBER)
                    .build();
            programList.add(program5);

            Program program6 = Program.builder()
                    .place("3번출구")
                    .title("3타의사")
                    .contentTitle("3번 입니다")
                    .phoneNumber("010-1234-1234")
                    .content("3번은 바로 3타의사")
                    .programCategory(HOSPITAL)
                    .teacherName("숭실인6")
                    .programS3Url(S3Info.builder()
                            .folderName("program/ACADEMY")
                            .fileName("third.jpg")
                            .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.comprogram/ACADEMY/third.jpg")
                            .build()
                    )
                    .teacherS3Url(
                            S3Info.builder()
                                    .folderName("teacher/ACADEMY")
                                    .fileName("third.jpg")
                                    .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/teacher/ACADEMY/third.jpg")
                                    .build()
                    )
                    .member(DUMMY_MEMBER)
                    .build();
            programList.add(program6);

            programRepository.saveAll(programList);
        }
    }
}
