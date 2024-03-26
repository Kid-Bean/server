package soongsil.kidbean.server.quiz.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Getter
public class ImageInfo {

    @Column(name = "image_url", length = 200)
    private String imageUrl;

    @Column(name = "file_name", length = 200)
    private String fileName;

    @Column(name = "folder_name", length = 100)
    private String folderName;

    @Builder
    public ImageInfo(String imageUrl, String fileName, String folderName) {
        this.imageUrl = imageUrl;
        this.fileName = fileName;
        this.folderName = folderName;
    }
}
