package soongsil.kidbean.server.global.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import io.findify.s3mock.S3Mock;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import soongsil.kidbean.server.global.application.config.AwsS3MockConfig;
import soongsil.kidbean.server.global.vo.ImageInfo;

@Slf4j
@Import(AwsS3MockConfig.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
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
    void S3_업로드_테스트() {
        // given
        MockMultipartFile file = new MockMultipartFile("test", path, contentType, "test".getBytes());

        //when
        String urlPath = s3Uploader.upload(file, folderName);

        // then
        assertThat(urlPath).contains(path);
        assertThat(urlPath).contains(folderName);
    }

    @Test
    void S3_삭제_테스트() {
        // given
        MockMultipartFile file = new MockMultipartFile("test", path, contentType, "test".getBytes());

        //when(생성된 파일을 제거)
        String urlPath = s3Uploader.upload(file, folderName);
        s3Uploader.deleteFile(generateImageInfo(urlPath));

        // then
        assertThatThrownBy(() -> amazonS3.getObject(BUCKET_NAME, urlPath))
                .isInstanceOf(AmazonS3Exception.class);
    }

    private ImageInfo generateImageInfo(String urlPath) {
        String generatedPath = urlPath.split("/" + BUCKET_NAME + "/" + folderName + "/")[1];
        return new ImageInfo(urlPath, generatedPath, folderName);
    }
}