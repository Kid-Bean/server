package soongsil.kidbean.server.global.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import soongsil.kidbean.server.global.exception.FileConvertFailException;
import soongsil.kidbean.server.global.domain.S3Info;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static soongsil.kidbean.server.global.exception.errorcode.GlobalErrorCode.FILE_CONVERT_FAIL;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {

    private static final DateTimeFormatter FILE_NAME_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    /**
     * MultipartFile을 로컬 파일 생성 없이 S3로 스트리밍 업로드한 뒤 저장 정보를 반환한다.
     */
    public S3Info upload(MultipartFile multipartFile, String folderName) {
        String fileName = createFileName(multipartFile.getOriginalFilename());
        String key = folderName + "/" + fileName;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(multipartFile.getContentType())
                .contentLength(multipartFile.getSize())
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();

        try (InputStream inputStream = multipartFile.getInputStream()) {
            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, multipartFile.getSize()));
        } catch (IOException e) {
            throw new FileConvertFailException(FILE_CONVERT_FAIL);
        }

        log.info("File Upload : {}", key);
        String uploadUrl = s3Client.utilities().getUrl(builder -> builder.bucket(bucket).key(key)).toString();

        return S3Info.builder()
                .s3Url(uploadUrl)
                .fileName(fileName)
                .folderName(folderName)
                .build();
    }

    public void deleteFile(S3Info s3Info) {
        String deleteFileName = s3Info.getFolderName() + "/" + s3Info.getFileName();
        log.info("delete fileName: {}", deleteFileName);
        s3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(deleteFileName)
                .build());
    }

    //LocalDateTime으로 파일 이름 중복 제거 (yyyyMMddHHmmss_원본파일명)
    private String createFileName(String originalFilename) {
        String formattedDateTime = LocalDateTime.now().format(FILE_NAME_FORMAT);
        return formattedDateTime + "_" + Objects.requireNonNull(originalFilename);
    }
}
