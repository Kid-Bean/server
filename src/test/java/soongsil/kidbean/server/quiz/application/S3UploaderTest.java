package soongsil.kidbean.server.quiz.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import io.findify.s3mock.S3Mock;
import java.io.IOException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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
        String dirName = "test";

        MockMultipartFile file = new MockMultipartFile("test", path, contentType, "test".getBytes());

        //when
        String urlPath = s3Uploader.upload(file, dirName);
        s3Uploader.deleteFile(path, dirName);

        // then
        assertThat(urlPath).contains(path);
        assertThat(urlPath).contains(dirName);
        //파일이 삭제 되었기 때문에 exception 발생
        assertThatThrownBy(() -> amazonS3.getObject(BUCKET_NAME, dirName + "/" + path))
                .isInstanceOf(AmazonS3Exception.class);
    }
}