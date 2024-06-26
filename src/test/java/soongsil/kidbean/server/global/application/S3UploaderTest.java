package soongsil.kidbean.server.global.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
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

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import({AwsS3MockConfig.class})
@SpringBootTest(classes = {S3Uploader.class})
class S3UploaderTest {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private S3Uploader s3Uploader;

    @Value("${cloud.aws.s3.bucket}")
    private String BUCKET_NAME;
    private final String path = "test.png";
    private final String contentType = "image/png";
    private final String folderName = "test";

    //S3Mock 서버 실행, 사용할 버킷 생성
    @BeforeAll
    void setUp(@Autowired S3Mock s3Mock, @Autowired AmazonS3 amazonS3) {
        s3Mock.start();
        amazonS3.createBucket(BUCKET_NAME);
    }

    //S3Mock 종료
    @AfterAll
    void tearDown(@Autowired S3Mock s3Mock, @Autowired AmazonS3 amazonS3) {
        amazonS3.shutdown();
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
        assertThatThrownBy(() -> amazonS3.getObject(BUCKET_NAME, s3Info.getS3Url()))
                .isInstanceOf(AmazonS3Exception.class);
    }
}