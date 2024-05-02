package soongsil.kidbean.server.program.application;

import jdk.jfr.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.program.domain.Program;
import soongsil.kidbean.server.program.domain.type.ProgramCategory;
import soongsil.kidbean.server.program.dto.request.EnrollProgramRequest;
import soongsil.kidbean.server.program.dto.request.UpdateProgramRequest;
import soongsil.kidbean.server.program.dto.response.ProgramListResponse;
import soongsil.kidbean.server.program.dto.response.ProgramDetailResponse;
import soongsil.kidbean.server.program.dto.response.ProgramResponse;
import soongsil.kidbean.server.program.repository.ProgramRepository;
import soongsil.kidbean.server.program.repository.StarRepository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ProgramService {

    private final StarRepository starRepository;
    private final ProgramRepository programRepository;
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

        return ProgramDetailResponse.from(program);
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
    public void createProgram(ProgramCategory programCategory, EnrollProgramRequest enrollProgramRequest) {

        Program createProgram = Program.builder()
                .title(enrollProgramRequest.title())
                .content(enrollProgramRequest.content())
                .place(enrollProgramRequest.place())
                .teacherName(enrollProgramRequest.teacherName())
                .phoneNumber(enrollProgramRequest.phoneNumber())
                .programCategory(programCategory)
                .build();

        programRepository.save(createProgram);
    }

    /**
     * 프로그램 수정하기. -> ex) 선생님 이름만 수정
     */
    public void editProgramInfo(Long programId, UpdateProgramRequest updateProgramRequest) {
        Program program = programRepository.findById(programId).orElseThrow(RuntimeException::new);

        //하나로 합치기 가능
        program.setTitle(updateProgramRequest.title());
        program.setContent(updateProgramRequest.content());

        programRepository.save(program); // save 안해도 set 때문에 자동으로 해결
    }
}