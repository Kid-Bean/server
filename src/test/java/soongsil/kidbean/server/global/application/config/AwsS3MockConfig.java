package soongsil.kidbean.server.global.application.config;

import io.findify.s3mock.S3Mock;
import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.auth.credentials.AnonymousCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@TestConfiguration
public class AwsS3MockConfig {

    @Value("${cloud.aws.region.static}")
    private String region;

    //9001번 포트 사용
    @Bean
    public S3Mock s3Mock() {
        return new S3Mock.Builder()
                .withPort(9001)
                .withInMemoryBackend()
                .build();
    }

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of(region))
                .endpointOverride(URI.create("http://localhost:9001"))
                .forcePathStyle(true)
                .credentialsProvider(AnonymousCredentialsProvider.create())
                .build();
    }
}
