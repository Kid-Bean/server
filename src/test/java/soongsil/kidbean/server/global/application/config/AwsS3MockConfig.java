package soongsil.kidbean.server.global.application.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import io.findify.s3mock.S3Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

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

    //@Primary 없으면 s3에 파일 생성됨
    @Primary
    @Bean
    public AmazonS3 amazonS3() {
        AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration(
                "http://localhost:9001", region);

        return AmazonS3ClientBuilder
                .standard()
                .withPathStyleAccessEnabled(true)
                .withEndpointConfiguration(endpoint)
                .withCredentials(new AWSStaticCredentialsProvider(new AnonymousAWSCredentials()))
                .build();
    }
}