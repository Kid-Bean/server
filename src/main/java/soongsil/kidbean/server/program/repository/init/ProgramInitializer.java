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
                    .programTitle("아이 마음치료")
                    .place("2번출구")
                    .contentTitle("마음의 평화")
                    .content("아이들의 마음을 치유하는 프로그램")
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
                    .departmentName("마음치료부")
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
                    .programTitle("아이 운동치료")
                    .place("2번출구")
                    .contentTitle("건강한 몸")
                    .content("아이들의 신체 발달을 돕는 프로그램")
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
                    .departmentName("운동치료부")
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
                    .programTitle("아이 언어치료")
                    .place("3번출구")
                    .contentTitle("말하는 즐거움")
                    .content("언어 발달을 돕는 프로그램")
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
                    .departmentName("언어치료부")
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
                    .programTitle("즐거운 미술 시간")
                    .place("아트룸")
                    .contentTitle("미술의 세계")
                    .content("다양한 미술 기법을 배우며 창의력을 키워봐요!")
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
                    .departmentName("미술부")
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
                    .programTitle("신나는 과학 실험")
                    .place("과학실")
                    .contentTitle("실험의 재미")
                    .content("다양한 실험을 통해 과학의 원리를 배워요!")
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
                    .departmentName("과학부")
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
                    .programTitle("창의적인 글쓰기")
                    .place("문예실")
                    .contentTitle("글쓰기의 재미")
                    .content("창의적인 글쓰기를 통해 표현력을 키워요!")
                    .programCategory(ACADEMY)
                    .programS3Info(S3Info.builder()
                            .folderName("program/ACADEMY")
                            .fileName("third.jpg")
                            .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/program/ACADEMY/third.jpg")
                            .build()
                    )
                    .build())
            .departmentInfo(DepartmentInfo.builder()
                    .phoneNumber("010-1234-1234")
                    .departmentName("문예부")
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
                    .programTitle("신나는 음악 시간")
                    .place("음악실")
                    .contentTitle("음악의 즐거움")
                    .content("다양한 악기를 배우며 음악의 즐거움을 느껴봐요!")
                    .programCategory(ACADEMY)
                    .programS3Info(S3Info.builder()
                            .folderName("program/ACADEMY")
                            .fileName("fourth.jpg")
                            .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/program/ACADEMY/fourth.jpg")
                            .build()
                    )
                    .build())
            .departmentInfo(DepartmentInfo.builder()
                    .phoneNumber("010-1234-1234")
                    .departmentName("음악부")
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
                    .programTitle("재미있는 체육 시간")
                    .place("체육관")
                    .contentTitle("운동의 즐거움")
                    .content("다양한 운동을 통해 건강을 지켜요!")
                    .programCategory(ACADEMY)
                    .programS3Info(S3Info.builder()
                            .folderName("program/ACADEMY")
                            .fileName("fifth.jpg")
                            .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/program/ACADEMY/fifth.jpg")
                            .build()
                    )
                    .build())
            .departmentInfo(DepartmentInfo.builder()
                    .phoneNumber("010-1234-1234")
                    .departmentName("체육부")
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
                    .programTitle("재미있는 요리 시간")
                    .place("쿠킹룸")
                    .contentTitle("요리의 즐거움")
                    .content("다양한 요리법을 배우며 요리의 즐거움을 느껴봐요!")
                    .programCategory(ACADEMY)
                    .programS3Info(S3Info.builder()
                            .folderName("program/ACADEMY")
                            .fileName("sixth.jpg")
                            .s3Url("https://kidbean.s3.ap-northeast-2.amazonaws.com/program/ACADEMY/sixth.jpg")
                            .build()
                    )
                    .build())
            .departmentInfo(DepartmentInfo.builder()
                    .phoneNumber("010-1234-1234")
                    .departmentName("요리부")
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
                    .programTitle("아이 음악치료")
                    .place("4번출구")
                    .contentTitle("음악의 힘")
                    .content("음악을 통해 아이들의 정서 발달을 돕는 프로그램")
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
                    .departmentName("음악치료부")
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
                    .programTitle("아이 미술치료")
                    .place("5번출구")
                    .contentTitle("창의력 키우기")
                    .content("미술 활동을 통해 창의력과 정서를 발달시키는 프로그램")
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
                    .departmentName("미술치료부")
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
                    .programTitle("아이 놀이치료")
                    .place("6번출구")
                    .contentTitle("즐거운 놀이")
                    .content("놀이를 통해 아이들의 사회성을 키우는 프로그램")
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
                    .departmentName("놀이 치료 센터")
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
