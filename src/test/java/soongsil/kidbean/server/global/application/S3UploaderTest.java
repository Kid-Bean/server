package soongsil.kidbean.server.global.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import io.findify.s3mock.S3Mock;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import soongsil.kidbean.server.global.application.config.AwsS3MockConfig;
import soongsil.kidbean.server.global.application.S3Uploader;
import soongsil.kidbean.server.global.vo.ImageInfo;

@Slf4j
@Import(AwsS3MockConfig.class)
@SpringBootTest
class S3UploaderTest {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private S3Uploader s3Uploader;

    private static final String BUCKET_NAME = "kidbean";

    //S3Mock 서버 실행, 사용할 버킷 생성
    @BeforeAll
    static void setUp(@Autowired S3Mock s3Mock, @Autowired AmazonS3 amazonS3) {
        s3Mock.start();
        amazonS3.createBucket(BUCKET_NAME);
    }

    //S3Mock 종료
    @AfterAll
    static void tearDown(@Autowired S3Mock s3Mock, @Autowired AmazonS3 amazonS3) {
        amazonS3.shutdown();
        s3Mock.stop();
    }

    @Test
    void S3_업로드_삭제_테스트() throws IOException {
        // given
        String path = "test.png";
        String contentType = "image/png";
        String folderName = "test";

        MockMultipartFile file = new MockMultipartFile("test", path, contentType, "test".getBytes());

        //when
        String urlPath = s3Uploader.upload(file, folderName);

        //생성된 파일을 제거
        String generatedPath = urlPath.split("/" + BUCKET_NAME + "/" + folderName + "/")[1];
        ImageInfo imageInfo = new ImageInfo(urlPath, generatedPath, folderName);
        s3Uploader.deleteFile(imageInfo);

        // then
        assertThat(urlPath).contains(path);
        assertThat(urlPath).contains(folderName);
        //파일이 삭제 되었기 때문에 exception 발생
        assertThatThrownBy(() -> amazonS3.getObject(BUCKET_NAME, urlPath))
                .isInstanceOf(AmazonS3Exception.class);
    }
}