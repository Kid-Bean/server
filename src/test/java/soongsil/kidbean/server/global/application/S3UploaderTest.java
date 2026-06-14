package soongsil.kidbean.server.global.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.findify.s3mock.S3Mock;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import soongsil.kidbean.server.global.application.config.AwsS3MockConfig;
import soongsil.kidbean.server.global.domain.S3Info;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import({AwsS3MockConfig.class})
@SpringBootTest(classes = {S3Uploader.class})
class S3UploaderTest {

    @Autowired
    private S3Client s3Client;

    @Autowired
    private S3Uploader s3Uploader;

    @Value("${cloud.aws.s3.bucket}")
    private String BUCKET_NAME;
    private final String path = "test.png";
    private final String contentType = "image/png";
    private final String folderName = "test";

    //S3Mock 서버 실행, 사용할 버킷 생성
    @BeforeAll
    void setUp(@Autowired S3Mock s3Mock) {
        s3Mock.start();
        s3Client.createBucket(builder -> builder.bucket(BUCKET_NAME));
    }

    //S3Mock 종료
    @AfterAll
    void tearDown(@Autowired S3Mock s3Mock) {
        s3Client.close();
        s3Mock.stop();
    }

    @Test
    @DisplayName("S3_업로드_테스트")
    void upload() {
        // given
        MockMultipartFile file = new MockMultipartFile("test", path, contentType, "test".getBytes());

        //when
        S3Info s3Info = s3Uploader.upload(file, folderName);

        // then
        assertThat(s3Info.getFolderName()).isEqualTo(folderName);
    }

    @Test
    @DisplayName("S3_삭제_테스트")
    void deleteFile() {
        // given
        MockMultipartFile file = new MockMultipartFile("test", path, contentType, "test".getBytes());

        //when(생성된 파일을 제거)
        S3Info s3Info = s3Uploader.upload(file, folderName);
        s3Uploader.deleteFile(s3Info);

        // then
        assertThatThrownBy(() -> s3Client.getObject(
                builder -> builder.bucket(BUCKET_NAME).key(s3Info.getS3Url())))
                .isInstanceOf(S3Exception.class);
    }
}
