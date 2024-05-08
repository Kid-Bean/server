package soongsil.kidbean.server.program.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import soongsil.kidbean.server.global.application.S3Uploader;
import soongsil.kidbean.server.global.vo.S3Info;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.program.domain.Day;
import soongsil.kidbean.server.program.domain.Program;
import soongsil.kidbean.server.program.domain.Star;
import soongsil.kidbean.server.program.domain.type.ProgramCategory;
import soongsil.kidbean.server.program.dto.request.EnrollProgramRequest;
import soongsil.kidbean.server.program.dto.request.UpdateProgramRequest;
import soongsil.kidbean.server.program.dto.response.ProgramListResponse;
import soongsil.kidbean.server.program.dto.response.ProgramDetailResponse;
import soongsil.kidbean.server.program.dto.response.ProgramResponse;
import soongsil.kidbean.server.program.dto.response.StarResponse;
import soongsil.kidbean.server.program.repository.DayRepository;
import soongsil.kidbean.server.program.repository.ProgramRepository;
import soongsil.kidbean.server.program.repository.StarRepository;

import java.util.List;
import java.util.stream.Collectors;

import static soongsil.kidbean.server.program.domain.type.ProgramCategory.HOSPITAL;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class ProgramService {

    private final ProgramRepository programRepository;

    private static final String COMMON_URL = "kidbean.s3.ap-northeast-2.amazonaws.com";
    private static final String PROGRAM_IMAGE_NAME = "program/";
    private static final String TEACHER_IMAGE_NAME = "teacher/";
    private final S3Uploader s3Uploader;
    private final DayRepository dayRepository;
    private final StarRepository starRepository;
    private final StarService starService;
    private final MemberRepository memberRepository;

    /**
     * 프로그램 상세 조회
     *
     * @param programId 프로그램 id
     * @return response
     */
    @Transactional
    public ProgramDetailResponse getProgramInfo(Long programId) {

        Program program = programRepository.findById(programId)
                .orElseThrow(RuntimeException::new);

        List<Day> date = dayRepository.findAllByProgram(program);

        //enum에서 가져옴
        List<String> dates = date.stream()
                .map(day -> day.getDate().getDate())
                .collect(Collectors.toList());

        return ProgramDetailResponse.of(program, dates);
    }

    /**
     * 카테고리에 따른 프로그램 목록 조회
     */
    @Transactional
    public ProgramListResponse getProgramListInfo(Long memberId,ProgramCategory programCategory, Pageable pageable) {

        Page<Program> programPage = programRepository.findAllByProgramCategory(programCategory, pageable);

        List<ProgramResponse> programResponseList = programPage.stream()
                .map(program -> {
                            List<StarResponse> starId = starService.getStars(memberId, program.getProgramId());
                            return ProgramResponse.of(program, (Star) starId);
                })
                .toList();

        return ProgramListResponse.from(programResponseList);
    }

    /**
     * 프로그램 삭제 - 관리자
     */
    @Transactional
    public ProgramDetailResponse deleteProgram(Long programId) {
        Program program = programRepository.findById(programId)
                .orElseThrow(RuntimeException::new);

        List<Day> date = dayRepository.findAllByProgram(program);

        //enum에서 가져옴
        List<String> dates = date.stream()
                .map(day -> day.getDate().getDate())
                .collect(Collectors.toList());

        return ProgramDetailResponse.of(program, dates);
    }

    /**
     * 프로그램 추가하기- 관리자
     */
    @Transactional
    public void createProgram(EnrollProgramRequest enrollProgramRequest,
                              MultipartFile programS3Url,
                              MultipartFile teacherS3Url) {

        if(enrollProgramRequest.programCategory() == HOSPITAL){

            String programFolderName = PROGRAM_IMAGE_NAME + "HOSPITAL";
            String programUploadUrl = s3Uploader.upload(programS3Url, programFolderName);
            String programGeneratedPath = programUploadUrl.split("/" + COMMON_URL + "/" + programFolderName + "/")[1];
            S3Info programImageInfo = S3Info.builder()
                    .s3Url(programUploadUrl)
                    .fileName(programGeneratedPath)
                    .folderName(PROGRAM_IMAGE_NAME + enrollProgramRequest.programCategory())
                    .build();

            Program createProgram = Program.builder()
                    .title(enrollProgramRequest.title())
                    .titleInfo(enrollProgramRequest.titleInfo())
                    .content(enrollProgramRequest.content())
                    .place(enrollProgramRequest.place())
                    .teacherName(enrollProgramRequest.teacherName())
                    .phoneNumber(enrollProgramRequest.phoneNumber())
                    .programCategory(enrollProgramRequest.programCategory())
                    .programImageInfo(programImageInfo)
                    .build();

            programRepository.save(createProgram);

        }else {
            String programFolderName = PROGRAM_IMAGE_NAME + "ACADEMY";
            String programUploadUrl = s3Uploader.upload(programS3Url, programFolderName);
            String programGeneratedPath = programUploadUrl.split("/" + COMMON_URL + "/" + programFolderName + "/")[1];
            S3Info programImageInfo = S3Info.builder()
                    .s3Url(programUploadUrl)
                    .fileName(programGeneratedPath)
                    .folderName(PROGRAM_IMAGE_NAME + enrollProgramRequest.programCategory())
                    .build();

            Program createProgram = Program.builder()
                    .title(enrollProgramRequest.title())
                    .titleInfo(enrollProgramRequest.titleInfo())
                    .content(enrollProgramRequest.content())
                    .place(enrollProgramRequest.place())
                    .teacherName(enrollProgramRequest.teacherName())
                    .phoneNumber(enrollProgramRequest.phoneNumber())
                    .programCategory(enrollProgramRequest.programCategory())
                    .programImageInfo(programImageInfo)
                    .build();

            programRepository.save(createProgram);
        }

        if(enrollProgramRequest.programCategory() == HOSPITAL){
            String teacherFolderName = TEACHER_IMAGE_NAME + "HOSPITAL";
            String teacherUploadUrl = s3Uploader.upload(teacherS3Url, teacherFolderName);
            String teacherGeneratedPath = teacherUploadUrl.split("/" + COMMON_URL + "/" + teacherFolderName + "/")[1];

            S3Info teacherImageInfo = S3Info.builder()
                    .s3Url(teacherUploadUrl)
                    .fileName(teacherGeneratedPath)
                    .folderName(TEACHER_IMAGE_NAME + enrollProgramRequest.programCategory())
                    .build();

            Program createProgram = Program.builder()
                    .title(enrollProgramRequest.title())
                    .content(enrollProgramRequest.content())
                    .place(enrollProgramRequest.place())
                    .teacherName(enrollProgramRequest.teacherName())
                    .phoneNumber(enrollProgramRequest.phoneNumber())
                    .programCategory(enrollProgramRequest.programCategory())
                    .teacherImageInfo(teacherImageInfo)
                    .build();

            programRepository.save(createProgram);
        }else{
            String teacherFolderName = TEACHER_IMAGE_NAME + "ACADEMY";
            String teacherUploadUrl = s3Uploader.upload(teacherS3Url, teacherFolderName);
            String teacherGeneratedPath = teacherUploadUrl.split("/" + COMMON_URL + "/" + teacherFolderName + "/")[1];

            S3Info teacherImageInfo = S3Info.builder()
                    .s3Url(teacherUploadUrl)
                    .fileName(teacherGeneratedPath)
                    .folderName(TEACHER_IMAGE_NAME + enrollProgramRequest.programCategory())
                    .build();

            Program createProgram = Program.builder()
                    .title(enrollProgramRequest.title())
                    .content(enrollProgramRequest.content())
                    .place(enrollProgramRequest.place())
                    .teacherName(enrollProgramRequest.teacherName())
                    .phoneNumber(enrollProgramRequest.phoneNumber())
                    .programCategory(enrollProgramRequest.programCategory())
                    .teacherImageInfo(teacherImageInfo)
                    .build();

            programRepository.save(createProgram);
        }
    }

    /**
     * 프로그램 수정하기. -> ex) 선생님 이름만 수정
     */
    public void editProgramInfo(Long programId, UpdateProgramRequest updateProgramRequest,
                                MultipartFile programS3Url,
                                MultipartFile teacherS3Url) {
        Program program = programRepository.findById(programId).orElseThrow(RuntimeException::new);


        if (programS3Url != null && !programS3Url.isEmpty()) {
            if(program.getProgramCategory() == HOSPITAL) {
                String programFolderName = PROGRAM_IMAGE_NAME + "HOSPITAL";
                String programUploadUrl = s3Uploader.upload(programS3Url, programFolderName);
                String programGeneratedPath = programUploadUrl.split("/" + COMMON_URL + "/" + programFolderName + "/")[1];
                S3Info programImageInfo = S3Info.builder()
                        .s3Url(programUploadUrl)
                        .fileName(programGeneratedPath)
                        .folderName(programFolderName)
                        .build();

                Program updateProgram = Program.builder()
                        .title(updateProgramRequest.title())
                        .content(updateProgramRequest.content())
                        .place(updateProgramRequest.place())
                        .teacherName(updateProgramRequest.teacherName())
                        .phoneNumber(updateProgramRequest.phoneNumber())
                        .programImageInfo(programImageInfo)
                        .build();

                programRepository.save(updateProgram);
            }else {
                String programFolderName = PROGRAM_IMAGE_NAME + "ACADEMY";
                String programUploadUrl = s3Uploader.upload(programS3Url, programFolderName);
                String programGeneratedPath = programUploadUrl.split("/" + COMMON_URL + "/" + programFolderName + "/")[1];
                S3Info programImageInfo = S3Info.builder()
                        .s3Url(programUploadUrl)
                        .fileName(programGeneratedPath)
                        .folderName(programFolderName)
                        .build();

                Program updateProgram = Program.builder()
                        .title(updateProgramRequest.title())
                        .content(updateProgramRequest.content())
                        .place(updateProgramRequest.place())
                        .teacherName(updateProgramRequest.teacherName())
                        .phoneNumber(updateProgramRequest.phoneNumber())
                        .programImageInfo(programImageInfo)
                        .build();

                programRepository.save(updateProgram);
            }

            if(teacherS3Url != null && !teacherS3Url.isEmpty()) {
                if (program.getProgramCategory() == HOSPITAL) {
                    String teacherFolderName = TEACHER_IMAGE_NAME + "HOSPITAL";
                    String teacherUploadUrl = s3Uploader.upload(teacherS3Url, teacherFolderName);
                    String teacherGeneratedPath = teacherUploadUrl.split("/" + COMMON_URL + "/" + teacherFolderName + "/")[1];

                    S3Info teacherImageInfo = S3Info.builder()
                            .s3Url(teacherUploadUrl)
                            .fileName(teacherGeneratedPath)
                            .folderName(teacherFolderName)
                            .build();

                    Program updateProgram = Program.builder()
                            .title(updateProgramRequest.title())
                            .content(updateProgramRequest.content())
                            .place(updateProgramRequest.place())
                            .teacherName(updateProgramRequest.teacherName())
                            .phoneNumber(updateProgramRequest.phoneNumber())
                            .teacherImageInfo(teacherImageInfo)
                            .build();

                    programRepository.save(updateProgram); // save 안해도 set 때문에 자동으로 해결
                }
            }else{
                String teacherFolderName = TEACHER_IMAGE_NAME + "ACADEMY";
                String teacherUploadUrl = s3Uploader.upload(teacherS3Url, teacherFolderName);
                String teacherGeneratedPath = teacherUploadUrl.split("/" + COMMON_URL + "/" + teacherFolderName + "/")[1];

                S3Info teacherImageInfo = S3Info.builder()
                        .s3Url(teacherUploadUrl)
                        .fileName(teacherGeneratedPath)
                        .folderName(teacherFolderName)
                        .build();

                Program updateProgram = Program.builder()
                        .title(updateProgramRequest.title())
                        .content(updateProgramRequest.content())
                        .place(updateProgramRequest.place())
                        .teacherName(updateProgramRequest.teacherName())
                        .phoneNumber(updateProgramRequest.phoneNumber())
                        .teacherImageInfo(teacherImageInfo)
                        .build();

                programRepository.save(updateProgram);
            }
        }
    }
}