package soongsil.kidbean.server.global.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class S3Info {

    @Column(name = "s3_url", length = 200)
    private String s3Url;

    @Column(name = "file_name", length = 200)
    private String fileName;

    @Column(name = "folder_name", length = 100)
    private String folderName;

    @Builder
    public S3Info(String s3Url, String fileName, String folderName) {
        this.s3Url = s3Url;
        this.fileName = fileName;
        this.folderName = folderName;
    }
}
