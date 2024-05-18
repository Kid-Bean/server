package soongsil.kidbean.server.program.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import soongsil.kidbean.server.global.application.S3Uploader;
import soongsil.kidbean.server.global.domain.S3Info;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.exception.MemberNotFoundException;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.program.domain.Program;
import soongsil.kidbean.server.program.domain.type.ProgramCategory;
import soongsil.kidbean.server.program.dto.request.EnrollProgramRequest;
import soongsil.kidbean.server.program.dto.request.UpdateProgramRequest;
import soongsil.kidbean.server.program.dto.response.ProgramDetailResponse;
import soongsil.kidbean.server.program.dto.response.ProgramResponseList;
import soongsil.kidbean.server.program.dto.response.ProgramResponse;
import soongsil.kidbean.server.program.exception.ProgramNotFoundException;
import soongsil.kidbean.server.program.repository.DayRepository;
import soongsil.kidbean.server.program.repository.ProgramRepository;

import java.util.List;

import static soongsil.kidbean.server.member.exception.errorcode.MemberErrorCode.MEMBER_NOT_FOUND;
import static soongsil.kidbean.server.program.exception.errorcode.ProgramErrorCode.PROGRAM_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class ProgramService {


    private static final String PROGRAM_IMAGE_NAME = "program/";
    private static final String DEPARTMENT_IMAGE_NAME = "department/";

    private final MemberRepository memberRepository;
    private final ProgramRepository programRepository;
    private final S3Uploader s3Uploader;
    private final DayRepository dayRepository;
    private final StarService starService;

    /**
     * 프로그램 상세 조회
     */
    public ProgramDetailResponse getProgramInfo(Long programId) {

        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new ProgramNotFoundException(PROGRAM_NOT_FOUND));

        List<String> dates = dayRepository.findAllByProgram(program).stream()
                .map(day -> day.getDate().getDayOfWeek())
                .toList();

        return ProgramDetailResponse.of(program, dates);
    }

    /**
     * 카테고리에 따른 프로그램 목록 조회
     */
    public ProgramResponseList getProgramInfoList(Long memberId, List<ProgramCategory> programCategoryList,
                                                  Pageable pageable) {

        Page<Program> programPage = programRepository.findAllByProgramInfo_ProgramCategoryIn(
                programCategoryList, pageable);

        if (!programPage.hasContent()) {
            throw new ProgramNotFoundException(PROGRAM_NOT_FOUND);
        }

        List<ProgramResponse> programResponseList = programPage.stream()
                .map(program -> ProgramResponse.of(program, starService.existsByMemberAndProgram(memberId, program)))
                .toList();

        return ProgramResponseList.from(programResponseList);
    }

    /**
     * 프로그램 삭제 - 관리자
     */
    public void deleteProgram(Long programId) {

        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new ProgramNotFoundException(PROGRAM_NOT_FOUND));

        s3Uploader.deleteFile(program.getProgramS3Info());
        s3Uploader.deleteFile(program.getDepartmentS3Info());

        programRepository.delete(program);
    }

    /**
     * 프로그램 추가 - 관리자
     */
    public void createProgram(Long memberId, EnrollProgramRequest enrollProgramRequest, MultipartFile programImage,
                              MultipartFile departmentImage) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));

        String programFolderName = PROGRAM_IMAGE_NAME + enrollProgramRequest.programCategory();
        S3Info programS3Info = s3Uploader.upload(programImage, programFolderName);

        String departmentFolderName = DEPARTMENT_IMAGE_NAME + enrollProgramRequest.programCategory();
        S3Info departmentS3Info = s3Uploader.upload(departmentImage, departmentFolderName);

        Program createProgram = enrollProgramRequest.toEntity(member);
        createProgram.setS3Info(programS3Info, departmentS3Info);

        programRepository.save(createProgram);
    }

    /**
     * 프로그램 수정하기. -> ex) 선생님 이름만 수정
     */
    public void editProgramInfo(UpdateProgramRequest updateProgramRequest, MultipartFile programImage,
                                MultipartFile departmentImage) {

        Program program = programRepository.findById(updateProgramRequest.programId())
                .orElseThrow(() -> new ProgramNotFoundException(PROGRAM_NOT_FOUND));

        if (!programImage.isEmpty()) {      //프로그램 이미지 변경
            updateProgramS3Info(programImage, program);
        }
        if (!departmentImage.isEmpty()) {  //department 이미지 변경
            updateDepartmentS3Info(departmentImage, program);
        }

        program.updateProgram(updateProgramRequest);
    }

    private void updateProgramS3Info(MultipartFile programImage, Program program) {
        s3Uploader.deleteFile(program.getProgramS3Info());

        String programFolderName = PROGRAM_IMAGE_NAME + program.getProgramCategory();
        S3Info programS3Info = s3Uploader.upload(programImage, programFolderName);

        program.setProgramS3Info(programS3Info);
    }

    private void updateDepartmentS3Info(MultipartFile departmentImage, Program program) {
        s3Uploader.deleteFile(program.getDepartmentS3Info());

        String departmentFolderName = DEPARTMENT_IMAGE_NAME + program.getProgramCategory();
        S3Info departmentS3Info = s3Uploader.upload(departmentImage, departmentFolderName);

        program.setDepartmentS3Info(departmentS3Info);
    }
}