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
import soongsil.kidbean.server.program.dto.response.ProgramDetailResponse;
import soongsil.kidbean.server.program.dto.response.ProgramListResponse;
import soongsil.kidbean.server.program.dto.response.ProgramResponse;
import soongsil.kidbean.server.program.exception.ProgramNotFoundException;
import soongsil.kidbean.server.program.repository.DayRepository;
import soongsil.kidbean.server.program.repository.ProgramRepository;

import java.util.List;

import static soongsil.kidbean.server.program.domain.type.ProgramCategory.HOSPITAL;
import static soongsil.kidbean.server.program.exception.errorcode.ProgramErrorCode.PROGRAM_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class ProgramService {

    private final ProgramRepository programRepository;

    private static final String PROGRAM_IMAGE_NAME = "program/";
    private static final String TEACHER_IMAGE_NAME = "teacher/";
    private final S3Uploader s3Uploader;
    private final DayRepository dayRepository;
    private final StarService starService;

    /**
     * 프로그램 상세 조회
     *
     * @param programId 프로그램 id
     * @return response
     */
    public ProgramDetailResponse getProgramInfo(Long programId) {

        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new ProgramNotFoundException(PROGRAM_NOT_FOUND));

        List<Day> date = dayRepository.findAllByProgram(program);

        //enum에서 가져옴
        List<String> dates = date.stream()
                .map(day -> day.getDate().getDate())
                .toList();

        return ProgramDetailResponse.of(program, dates);
    }

    /**
     * 카테고리에 따른 프로그램 목록 조회
     */
    public ProgramListResponse getProgramListInfo(Long memberId, ProgramCategory programCategory, Pageable pageable) {

        Page<Program> programPage = programRepository.findAllByProgramCategory(programCategory, pageable);

        List<ProgramResponse> programResponseList = programPage.stream()
                .map(program -> ProgramResponse.of(program, starService.existsByMemberAndProgram(memberId, program)))
                .toList();

        return ProgramListResponse.from(programResponseList);
    }

    /**
     * 프로그램 삭제 - 관리자
     */
    public void deleteProgram(Long programId) {
        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new ProgramNotFoundException(PROGRAM_NOT_FOUND));

        programRepository.delete(program);
    }

    /**
     * 프로그램 추가하기- 관리자
     */
    public void createProgram(EnrollProgramRequest enrollProgramRequest,
                              MultipartFile programS3Url,
                              MultipartFile teacherS3Url) {

        if (enrollProgramRequest.programCategory() == HOSPITAL) {

            String programFolderName = PROGRAM_IMAGE_NAME + "HOSPITAL";
            S3Info programImageInfo = s3Uploader.upload(programS3Url, programFolderName);

            Program createProgram = Program.builder()
                    .title(enrollProgramRequest.title())
                    .contentTitle(enrollProgramRequest.titleInfo())
                    .content(enrollProgramRequest.content())
                    .place(enrollProgramRequest.place())
                    .teacherName(enrollProgramRequest.teacherName())
                    .phoneNumber(enrollProgramRequest.phoneNumber())
                    .programCategory(enrollProgramRequest.programCategory())
                    .programS3Url(programImageInfo)
                    .build();

            programRepository.save(createProgram);

        } else {
            String programFolderName = PROGRAM_IMAGE_NAME + "ACADEMY";
            S3Info programImageInfo = s3Uploader.upload(programS3Url, programFolderName);

            Program createProgram = Program.builder()
                    .title(enrollProgramRequest.title())
                    .contentTitle(enrollProgramRequest.titleInfo())
                    .content(enrollProgramRequest.content())
                    .place(enrollProgramRequest.place())
                    .teacherName(enrollProgramRequest.teacherName())
                    .phoneNumber(enrollProgramRequest.phoneNumber())
                    .programCategory(enrollProgramRequest.programCategory())
                    .programS3Url(programImageInfo)
                    .build();

            programRepository.save(createProgram);
        }

        if (enrollProgramRequest.programCategory() == HOSPITAL) {
            String teacherFolderName = TEACHER_IMAGE_NAME + "HOSPITAL";
            S3Info teacherImageInfo = s3Uploader.upload(teacherS3Url, teacherFolderName);

            Program createProgram = Program.builder()
                    .title(enrollProgramRequest.title())
                    .content(enrollProgramRequest.content())
                    .place(enrollProgramRequest.place())
                    .teacherName(enrollProgramRequest.teacherName())
                    .phoneNumber(enrollProgramRequest.phoneNumber())
                    .programCategory(enrollProgramRequest.programCategory())
                    .teacherS3Url(teacherImageInfo)
                    .build();

            programRepository.save(createProgram);
        } else {
            String teacherFolderName = TEACHER_IMAGE_NAME + "ACADEMY";
            S3Info teacherImageInfo = s3Uploader.upload(teacherS3Url, teacherFolderName);

            Program createProgram = Program.builder()
                    .title(enrollProgramRequest.title())
                    .content(enrollProgramRequest.content())
                    .place(enrollProgramRequest.place())
                    .teacherName(enrollProgramRequest.teacherName())
                    .phoneNumber(enrollProgramRequest.phoneNumber())
                    .programCategory(enrollProgramRequest.programCategory())
                    .teacherS3Url(teacherImageInfo)
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
        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new ProgramNotFoundException(PROGRAM_NOT_FOUND));


        if (programS3Url != null && !programS3Url.isEmpty()) {
            if (program.getProgramCategory() == HOSPITAL) {
                String programFolderName = PROGRAM_IMAGE_NAME + "HOSPITAL";
                S3Info programImageInfo = s3Uploader.upload(programS3Url, programFolderName);

                Program updateProgram = Program.builder()
                        .title(updateProgramRequest.title())
                        .content(updateProgramRequest.content())
                        .place(updateProgramRequest.place())
                        .teacherName(updateProgramRequest.teacherName())
                        .phoneNumber(updateProgramRequest.phoneNumber())
                        .programS3Url(programImageInfo)
                        .build();

                programRepository.save(updateProgram);
            } else {
                String programFolderName = PROGRAM_IMAGE_NAME + "ACADEMY";
                S3Info programImageInfo = s3Uploader.upload(programS3Url, programFolderName);

                Program updateProgram = Program.builder()
                        .title(updateProgramRequest.title())
                        .content(updateProgramRequest.content())
                        .place(updateProgramRequest.place())
                        .teacherName(updateProgramRequest.teacherName())
                        .phoneNumber(updateProgramRequest.phoneNumber())
                        .programS3Url(programImageInfo)
                        .build();

                programRepository.save(updateProgram);
            }

            if (teacherS3Url != null && !teacherS3Url.isEmpty()) {
                if (program.getProgramCategory() == HOSPITAL) {
                    String teacherFolderName = TEACHER_IMAGE_NAME + "HOSPITAL";
                    S3Info teacherImageInfo = s3Uploader.upload(teacherS3Url, teacherFolderName);

                    Program updateProgram = Program.builder()
                            .title(updateProgramRequest.title())
                            .content(updateProgramRequest.content())
                            .place(updateProgramRequest.place())
                            .teacherName(updateProgramRequest.teacherName())
                            .phoneNumber(updateProgramRequest.phoneNumber())
                            .teacherS3Url(teacherImageInfo)
                            .build();

                    programRepository.save(updateProgram);
                }
            } else {
                String teacherFolderName = TEACHER_IMAGE_NAME + "ACADEMY";
                S3Info teacherImageInfo = s3Uploader.upload(teacherS3Url, teacherFolderName);

                Program updateProgram = Program.builder()
                        .title(updateProgramRequest.title())
                        .content(updateProgramRequest.content())
                        .place(updateProgramRequest.place())
                        .teacherName(updateProgramRequest.teacherName())
                        .phoneNumber(updateProgramRequest.phoneNumber())
                        .teacherS3Url(teacherImageInfo)
                        .build();

                programRepository.save(updateProgram);
            }
        }
    }
}