package soongsil.kidbean.server.program.repository.init;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import soongsil.kidbean.server.global.domain.S3Info;
import soongsil.kidbean.server.global.util.LocalDummyDataInit;
import soongsil.kidbean.server.program.domain.Program;
import soongsil.kidbean.server.program.domain.vo.DepartmentInfo;
import soongsil.kidbean.server.program.domain.vo.ProgramInfo;
import soongsil.kidbean.server.program.repository.ProgramRepository;

import java.util.ArrayList;
import java.util.List;

import static soongsil.kidbean.server.member.repository.init.MemberInitializer.DUMMY_ADMIN;
import static soongsil.kidbean.server.member.repository.init.MemberInitializer.DUMMY_MEMBER;
import static soongsil.kidbean.server.program.domain.type.ProgramCategory.ACADEMY;
import static soongsil.kidbean.server.program.domain.type.ProgramCategory.HOSPITAL;

@Slf4j
@RequiredArgsConstructor
@Order(2)
@LocalDummyDataInit
public class ProgramInitializer implements ApplicationRunner {

    private final ProgramRepository programRepository;

    public static final Program PROGRAM1 = Program.builder()
            .programInfo(ProgramInfo.builder()
                    .programTitle("1타 의사")
                    .place("1번출구")
                    .contentTitle("1번 입니다")
                    .content("1번은 바로 1타 의사")
                    .programCategory(HOSPITAL)
                    .programS3Info(S3Info.builder()
                            .folderName("program/HOSPITAL")
                            .fileName("first.jpg")
                            .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/program/HOSPITAL/first.jpg")
                            .build()
                    )
                    .build())
            .departmentInfo(DepartmentInfo.builder()
                    .phoneNumber("010-1234-1234")
                    .departmentName("숭실인1")
                    .departmentS3Info(
                            S3Info.builder()
                                    .folderName("department/HOSPITAL")
                                    .fileName("first.jpg")
                                    .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/department/HOSPITAL/first.jpg")
                                    .build()
                    )
                    .build())
            .member(DUMMY_MEMBER)
            .build();

    public static final Program PROGRAM2 = Program.builder()
            .programInfo(ProgramInfo.builder()
                    .programTitle("2타 의사")
                    .place("2번출구")
                    .contentTitle("2번 입니다")
                    .content("2번은 바로 2타 의사")
                    .programCategory(HOSPITAL)
                    .programS3Info(S3Info.builder()
                            .folderName("program/HOSPITAL")
                            .fileName("second.jpg")
                            .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/program/HOSPITAL/second.jpg")
                            .build()
                    )
                    .build())
            .departmentInfo(DepartmentInfo.builder()
                    .phoneNumber("010-1234-1234")
                    .departmentName("숭실인2")
                    .departmentS3Info(
                            S3Info.builder()
                                    .folderName("department/HOSPITAL")
                                    .fileName("second.jpg")
                                    .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/department/HOSPITAL/second.jpg")
                                    .build()
                    )
                    .build())
            .member(DUMMY_MEMBER)
            .build();

    public static final Program PROGRAM3 = Program.builder()
            .programInfo(ProgramInfo.builder()
                    .programTitle("3타 의사")
                    .place("3번출구")
                    .contentTitle("3번 입니다")
                    .content("3번은 바로 3타 의사")
                    .programCategory(HOSPITAL)
                    .programS3Info(S3Info.builder()
                            .folderName("program/HOSPITAL")
                            .fileName("third.jpg")
                            .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/program/HOSPITAL/third.jpg")
                            .build()
                    )
                    .build())
            .departmentInfo(DepartmentInfo.builder()
                    .phoneNumber("010-1234-1234")
                    .departmentName("숭실인3")
                    .departmentS3Info(
                            S3Info.builder()
                                    .folderName("department/HOSPITAL")
                                    .fileName("third.jpg")
                                    .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/department/HOSPITAL/third.jpg")
                                    .build()
                    )
                    .build())
            .member(DUMMY_MEMBER)
            .build();

    public static final Program PROGRAM4 = Program.builder()
            .programInfo(ProgramInfo.builder()
                    .programTitle("1타 강사")
                    .place("1번출구")
                    .contentTitle("1번 입니다")
                    .content("1번은 바로 1타 강사")
                    .programCategory(ACADEMY)
                    .programS3Info(S3Info.builder()
                            .folderName("program/ACADEMY")
                            .fileName("first.jpg")
                            .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/program/ACADEMY/first.jpg")
                            .build()
                    )
                    .build())
            .departmentInfo(DepartmentInfo.builder()
                    .phoneNumber("010-1234-1234")
                    .departmentName("숭실인4")
                    .departmentS3Info(
                            S3Info.builder()
                                    .folderName("department/ACADEMY")
                                    .fileName("first.jpg")
                                    .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/department/ACADEMY/first.jpg")
                                    .build()
                    )
                    .build())
            .member(DUMMY_MEMBER)
            .build();

    public static final Program PROGRAM5 = Program.builder()
            .programInfo(ProgramInfo.builder()
                    .programTitle("2타 강사")
                    .place("2번출구")
                    .contentTitle("2번 입니다")
                    .content("2번은 바로 2타 강사")
                    .programCategory(ACADEMY)
                    .programS3Info(S3Info.builder()
                            .folderName("program/ACADEMY")
                            .fileName("second.jpg")
                            .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/program/ACADEMY/second.jpg")
                            .build()
                    )
                    .build())
            .departmentInfo(DepartmentInfo.builder()
                    .phoneNumber("010-1234-1234")
                    .departmentName("숭실인5")
                    .departmentS3Info(
                            S3Info.builder()
                                    .folderName("department/ACADEMY")
                                    .fileName("second.jpg")
                                    .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/department/ACADEMY/second.jpg")
                                    .build()
                    )
                    .build())
            .member(DUMMY_MEMBER)
            .build();

    public static final Program PROGRAM6 = Program.builder()
            .programInfo(ProgramInfo.builder()
                    .programTitle("3타 강사")
                    .place("3번출구")
                    .contentTitle("3번 입니다")
                    .content("3번은 바로 3타 강사")
                    .programCategory(ACADEMY)
                    .programS3Info(S3Info.builder()
                            .folderName("program/ACADEMY")
                            .fileName("third.jpg")
                            .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/program/program/ACADEMY/third.jpg")
                            .build()
                    )
                    .build())
            .departmentInfo(DepartmentInfo.builder()
                    .phoneNumber("010-1234-1234")
                    .departmentName("숭실인6")
                    .departmentS3Info(
                            S3Info.builder()
                                    .folderName("department/ACADEMY")
                                    .fileName("third.jpg")
                                    .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/department/ACADEMY/third.jpg")
                                    .build()
                    )
                    .build())
            .member(DUMMY_ADMIN)
            .build();

    public static final Program PROGRAM7 = Program.builder()
            .programInfo(ProgramInfo.builder()
                    .programTitle("4타 강사")
                    .place("4번출구")
                    .contentTitle("4번 입니다")
                    .content("4번은 바로 4타 강사")
                    .programCategory(ACADEMY)
                    .programS3Info(S3Info.builder()
                            .folderName("program/ACADEMY")
                            .fileName("fourth.jpg")
                            .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/program/program/ACADEMY/fourth.jpg")
                            .build()
                    )
                    .build())
            .departmentInfo(DepartmentInfo.builder()
                    .phoneNumber("010-1234-1234")
                    .departmentName("숭실인7")
                    .departmentS3Info(
                            S3Info.builder()
                                    .folderName("department/ACADEMY")
                                    .fileName("fourth.jpg")
                                    .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/department/ACADEMY/fourth.jpg")
                                    .build()
                    )
                    .build())
            .member(DUMMY_ADMIN)
            .build();

    public static final Program PROGRAM8 = Program.builder()
            .programInfo(ProgramInfo.builder()
                    .programTitle("5타 강사")
                    .place("5번출구")
                    .contentTitle("5번 입니다")
                    .content("5번은 바로 5타 강사")
                    .programCategory(ACADEMY)
                    .programS3Info(S3Info.builder()
                            .folderName("program/ACADEMY")
                            .fileName("fifth.jpg")
                            .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/program/program/ACADEMY/fifth.jpg")
                            .build()
                    )
                    .build())
            .departmentInfo(DepartmentInfo.builder()
                    .phoneNumber("010-1234-1234")
                    .departmentName("숭실인8")
                    .departmentS3Info(
                            S3Info.builder()
                                    .folderName("department/ACADEMY")
                                    .fileName("fifth.jpg")
                                    .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/department/ACADEMY/fifth.jpg")
                                    .build()
                    )
                    .build())
            .member(DUMMY_ADMIN)
            .build();

    public static final Program PROGRAM9 = Program.builder()
            .programInfo(ProgramInfo.builder()
                    .programTitle("6타 강사")
                    .place("6번출구")
                    .contentTitle("6번 입니다")
                    .content("6번은 바로 6타 강사")
                    .programCategory(ACADEMY)
                    .programS3Info(S3Info.builder()
                            .folderName("program/ACADEMY")
                            .fileName("sixth.jpg")
                            .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/program/program/ACADEMY/sixth.jpg")
                            .build()
                    )
                    .build())
            .departmentInfo(DepartmentInfo.builder()
                    .phoneNumber("010-1234-1234")
                    .departmentName("숭실인9")
                    .departmentS3Info(
                            S3Info.builder()
                                    .folderName("department/ACADEMY")
                                    .fileName("sixth.jpg")
                                    .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/department/ACADEMY/sixth.jpg")
                                    .build()
                    )
                    .build())
            .member(DUMMY_ADMIN)
            .build();

    public static final Program PROGRAM10 = Program.builder()
            .programInfo(ProgramInfo.builder()
                    .programTitle("4타 의사")
                    .place("4번출구")
                    .contentTitle("4번 입니다")
                    .content("4번은 바로 4타 의사")
                    .programCategory(HOSPITAL)
                    .programS3Info(S3Info.builder()
                            .folderName("program/HOSPITAL")
                            .fileName("third.jpg")
                            .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/program/HOSPITAL/fourth.jpg")
                            .build()
                    )
                    .build())
            .departmentInfo(DepartmentInfo.builder()
                    .phoneNumber("010-1234-1234")
                    .departmentName("숭실인10")
                    .departmentS3Info(
                            S3Info.builder()
                                    .folderName("department/HOSPITAL")
                                    .fileName("fourth.jpg")
                                    .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/department/HOSPITAL/fourth.jpg")
                                    .build()
                    )
                    .build())
            .member(DUMMY_MEMBER)
            .build();

    public static final Program PROGRAM11 = Program.builder()
            .programInfo(ProgramInfo.builder()
                    .programTitle("5타 의사")
                    .place("5번출구")
                    .contentTitle("5번 입니다")
                    .content("5번은 바로 5타 의사")
                    .programCategory(HOSPITAL)
                    .programS3Info(S3Info.builder()
                            .folderName("program/HOSPITAL")
                            .fileName("fifth.jpg")
                            .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/program/HOSPITAL/fifth.jpg")
                            .build()
                    )
                    .build())
            .departmentInfo(DepartmentInfo.builder()
                    .phoneNumber("010-1234-1234")
                    .departmentName("숭실인11")
                    .departmentS3Info(
                            S3Info.builder()
                                    .folderName("department/HOSPITAL")
                                    .fileName("fourth.jpg")
                                    .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/department/HOSPITAL/fifth.jpg")
                                    .build()
                    )
                    .build())
            .member(DUMMY_MEMBER)
            .build();

    public static final Program PROGRAM12 = Program.builder()
            .programInfo(ProgramInfo.builder()
                    .programTitle("6타 의사")
                    .place("6번출구")
                    .contentTitle("6번 입니다")
                    .content("6번은 바로 6타 의사")
                    .programCategory(HOSPITAL)
                    .programS3Info(S3Info.builder()
                            .folderName("program/HOSPITAL")
                            .fileName("sixth.jpg")
                            .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/program/HOSPITAL/sixth.jpg")
                            .build()
                    )
                    .build())
            .departmentInfo(DepartmentInfo.builder()
                    .phoneNumber("010-1234-1234")
                    .departmentName("숭실인11")
                    .departmentS3Info(
                            S3Info.builder()
                                    .folderName("department/HOSPITAL")
                                    .fileName("sixth.jpg")
                                    .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/department/HOSPITAL/sixth.jpg")
                                    .build()
                    )
                    .build())
            .member(DUMMY_MEMBER)
            .build();

    @Override
    public void run(ApplicationArguments args) {
        if (programRepository.count() > 0) {
            log.info("[Program]더미 데이터 존재");
        } else {
            List<Program> programList = new ArrayList<>();

            programList.add(PROGRAM1);
            programList.add(PROGRAM2);
            programList.add(PROGRAM3);
            programList.add(PROGRAM4);
            programList.add(PROGRAM5);
            programList.add(PROGRAM6);
            programList.add(PROGRAM7);
            programList.add(PROGRAM8);
            programList.add(PROGRAM9);
            programList.add(PROGRAM10);
            programList.add(PROGRAM11);
            programList.add(PROGRAM12);

            programRepository.saveAll(programList);
        }
    }
}
