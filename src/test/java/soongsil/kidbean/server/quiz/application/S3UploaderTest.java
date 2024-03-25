package soongsil.kidbean.server.quiz.application;

import static org.assertj.core.api.Assertions.assertThat;

import io.findify.s3mock.S3Mock;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import soongsil.kidbean.server.quiz.application.config.AwsS3MockConfig;

@Import(AwsS3MockConfig.class)
@SpringBootTest
class S3UploaderTest {

    @Autowired
    private S3Uploader s3Uploader;

    @Autowired
    S3Mock s3Mock;

    @AfterEach
    public void shutdownMockS3() {
        s3Mock.stop();
    }

    @Test
    void 로컬환경에서_S3_업로드_테스트_S3버킷에_파일업로드_하지않음() throws IOException {
        // given
        String path = "test.png";
        String contentType = "image/png";
        String dirName = "test";

        MockMultipartFile file = new MockMultipartFile("test", path, contentType, "test".getBytes());

        //when
        String urlPath = s3Uploader.upload(file, dirName);

        // then
        assertThat(urlPath).contains(path);
        assertThat(urlPath).contains(dirName);
    }

    @Test
    void deleteFile() {
    }
}