package soongsil.kidbean.server.program.application;

import jdk.jfr.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.program.domain.Program;
import soongsil.kidbean.server.program.domain.type.ProgramCategory;
import soongsil.kidbean.server.program.dto.response.ProgramListResponse;
import soongsil.kidbean.server.program.dto.response.ProgramDetailResponse;
import soongsil.kidbean.server.program.dto.response.ProgramResponse;
import soongsil.kidbean.server.program.repository.ProgramRepository;

import javax.swing.*;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ProgramService {

    private final ProgramRepository programRepository;

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

        return ProgramDetailResponse.from(program);
    }

    /**
     *  카테고리에 따른 프로그램 목록 조회
     *  */
    @Transactional
    public ProgramListResponse getProgramListInfo(ProgramCategory programCategory) {
        List<Program> programList = programRepository.findAllByProgramCategory(programCategory);
        return ProgramListResponse.from(programList);
    }

//    /**
//     * image upload
//     * @param multipartFile
//     * @return
//     */
//    private S3Info uploadFile( MultipartFile multipartFile) {
//        S3Uploader s3Uploader;
//        String folderName = s3Uploader.upload(multipartFile);
//        String uploadUrl = s3Uploader.upload(multipartFile, folderName);
//        String fileName = uploadUrl.split(folderName + "/")[1];
//
//        return S3Info.builder()
//                .folderName(folderName)
//                .fileName(fileName)
//                .s3Url(uploadUrl)
//                .build();
//    }

    /**
     * 프로그램 추가하기- 관리자
     */
    @Transactional
        public ProgramDetailResponse createProgram(Category category,Program program) {

            Program createProgram = Program.builder()
                    .title(program.getTitle())
                    .content(program.getContent())
                    .programImageInfo(program.getProgramImageInfo())
                    .teacherImageInfo(program.getTeacherImageInfo())
                    .build();

            programRepository.save(createProgram);
            log.info("추가!!");
            return ProgramDetailResponse.from(createProgram);
    }

    /**
     * 프로그램 수정하기. -> ex) 선생님 이름만 수정
     */
    public ProgramDetailResponse editProgramInfo(Long programId, Program editprogram){
        Program program = programRepository.findById(programId)
                .orElseThrow(RuntimeException::new);

        program.setTitle(editprogram.getTitle());
        program.setContent(editprogram.getContent());
        program.setTeacherImageInfo(editprogram.getTeacherImageInfo());
        program.setProgramImageInfo(editprogram.getProgramImageInfo());

        programRepository.save(program);
        log.info("수정완료");
        return ProgramDetailResponse.from(program);
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

}