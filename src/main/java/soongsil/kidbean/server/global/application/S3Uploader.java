package soongsil.kidbean.server.global.application;

import static soongsil.kidbean.server.global.exception.errorcode.GlobalErrorCode.*;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import soongsil.kidbean.server.global.vo.S3Info;
import soongsil.kidbean.server.global.exception.FileConvertFailException;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {

    private final AmazonS3 amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    //MultipartFile을 전달받아 File로 전환한 후 S3에 업로드 후 url return
    public S3Info upload(MultipartFile multipartFile, String folderName) {
        File uploadFile;

        try {
            uploadFile = convert(multipartFile)
                    .orElseThrow(() -> new FileConvertFailException(FILE_CONVERT_FAIL));
        } catch (IOException e) {
            throw new FileConvertFailException(FILE_CONVERT_FAIL);
        }

        String uploadUrl = upload(uploadFile, folderName);
        String fileName = uploadFile.getName();

        return S3Info.builder()
                .s3Url(uploadUrl)
                .fileName(fileName)
                .folderName(folderName)
                .build();
    }

    //S3 버킷에 있는 파일을 삭제
    public void deleteFile(S3Info s3Info) {
        String deleteFileName = s3Info.getFolderName() + "/" + s3Info.getFileName();
        log.info("delete fileName: {}", deleteFileName);
        amazonS3Client.deleteObject(bucket, deleteFileName);
    }

    private String upload(File uploadFile, String folderName) {
        String fileName = folderName + "/" + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fileName);
        //로컬에 생성된 File 삭제 (MultipartFile -> File 전환 하며 로컬에 파일 생성됨)
        removeLocalNewFile(uploadFile);

        //업로드된 파일의 S3 URL 주소 반환
        return uploadImageUrl;
    }

    private String putS3(File uploadFile, String fileName) {
        //PublicRead 권한으로 업로드
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, uploadFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );
        log.info("File Upload : " + fileName);

        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeLocalNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("로컬 파일이 삭제되었습니다.");
        } else {
            log.info("로컬 파일이 삭제되지 못했습니다.");
        }
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        //UUID로 파일 이름 중복 제거
        String fileName = UUID.randomUUID() + Objects.requireNonNull(file.getOriginalFilename());
        File convertFile = new File(fileName);

        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }
}