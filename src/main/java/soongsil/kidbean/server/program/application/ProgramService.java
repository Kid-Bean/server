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
import soongsil.kidbean.server.program.domain.Day;
import soongsil.kidbean.server.program.domain.Program;
import soongsil.kidbean.server.program.domain.type.ProgramCategory;
import soongsil.kidbean.server.program.dto.request.EnrollProgramRequest;
import soongsil.kidbean.server.program.dto.request.UpdateProgramRequest;
import soongsil.kidbean.server.program.dto.response.ProgramListResponse;
import soongsil.kidbean.server.program.dto.response.ProgramDetailResponse;
import soongsil.kidbean.server.program.dto.response.ProgramResponse;
import soongsil.kidbean.server.program.repository.DayRepository;
import soongsil.kidbean.server.program.repository.ProgramRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ProgramService {

    private final ProgramRepository programRepository;

    private static final String COMMON_URL = "kidbean.s3.ap-northeast-2.amazonaws.com";
    private static final String PROGRAM_IMAGE_NAME = "program/";
    private static final String TEACHER_IMAGE_NAME = "teacher/";
    private final S3Uploader s3Uploader;
    private final DayRepository dayRepository;

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

        return ProgramDetailResponse.of(program,dates);
    }

    /**
     * 카테고리에 따른 프로그램 목록 조회
     */
    @Transactional
    public ProgramListResponse getProgramListInfo(ProgramCategory programCategory, Pageable pageable) {

        Page<Program> programPage = programRepository.findAllByProgramCategory(programCategory, pageable);

        List<ProgramResponse> programResponseList = programPage.stream()
                .map(ProgramResponse::from)
                .toList();

        return ProgramListResponse.from(programResponseList);
    }

    /**
     * 프로그램 삭제 - 관리자
     */
    @Transactional
    public ProgramResponse deleteProgram(Long programId) {
        Program program = programRepository.findById(programId)
                .orElseThrow(RuntimeException::new);

        programRepository.delete(program);

        return ProgramResponse.from(program);
    }

    /**
     * 프로그램 추가하기- 관리자
     */
    @Transactional
    public void createProgram(EnrollProgramRequest enrollProgramRequest, MultipartFile s3Url) {

        String programFolderName = PROGRAM_IMAGE_NAME + enrollProgramRequest.programCategory();
        String teacherFolderName = TEACHER_IMAGE_NAME + enrollProgramRequest.programCategory();

        String programUploadUrl = s3Uploader.upload(s3Url, programFolderName);
        String teacherUploadUrl = s3Uploader.upload(s3Url, teacherFolderName);

        String programGeneratedPath = programUploadUrl.split("/" + COMMON_URL + "/" + programFolderName + "/")[1];
        String teacherGeneratedPath = teacherUploadUrl.split("/" + COMMON_URL + "/" + teacherFolderName + "/")[1];


        S3Info programImageInfo = S3Info.builder()
                .s3Url(programUploadUrl)
                .fileName(programGeneratedPath)
                .folderName(PROGRAM_IMAGE_NAME + enrollProgramRequest.programCategory())
                .build();

        S3Info teacherImageInfo = S3Info.builder()
                .s3Url(teacherUploadUrl)
                .fileName(teacherGeneratedPath)
                .folderName(TEACHER_IMAGE_NAME + enrollProgramRequest.programCategory())
                .build();

        //to-entity
        Program createProgram = Program.builder()
                .title(enrollProgramRequest.title())
                .content(enrollProgramRequest.content())
                .place(enrollProgramRequest.place())
                .teacherName(enrollProgramRequest.teacherName())
                .phoneNumber(enrollProgramRequest.phoneNumber())
                .programCategory(enrollProgramRequest.programCategory())
                .programImageInfo(programImageInfo)
                .teacherImageInfo(teacherImageInfo)
                .build();

        programRepository.save(createProgram);
    }

    /**
     * 프로그램 수정하기. -> ex) 선생님 이름만 수정
     */
    public void editProgramInfo(Long programId, UpdateProgramRequest updateProgramRequest, MultipartFile s3Url) {
        Program program = programRepository.findById(programId).orElseThrow(RuntimeException::new);


        if (s3Url != null && !s3Url.isEmpty()) {
            String programFolderName = PROGRAM_IMAGE_NAME + program.getProgramCategory();
            String teacherFolderName = TEACHER_IMAGE_NAME + program.getProgramCategory();

            String programUploadUrl = s3Uploader.upload(s3Url, programFolderName);
            String teacherUploadUrl = s3Uploader.upload(s3Url, teacherFolderName);

            String programGeneratedPath = programUploadUrl.split("/" + COMMON_URL + "/" + programFolderName + "/")[1];
            String teacherGeneratedPath = teacherUploadUrl.split("/" + COMMON_URL + "/" + teacherFolderName + "/")[1];

            S3Info programImageInfo = S3Info.builder()
                    .s3Url(programUploadUrl)
                    .fileName(programGeneratedPath)
                    .folderName(programFolderName)
                    .build();

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
                    .programImageInfo(programImageInfo)
                    .teacherImageInfo(teacherImageInfo)
                    .build();

            programRepository.save(updateProgram); // save 안해도 set 때문에 자동으로 해결
        }
    }
}