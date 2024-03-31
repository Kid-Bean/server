package soongsil.kidbean.server.global.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Getter
public class ImageInfo2 {

    @Column(name = "teacher_image_url", length = 50)
    private String teacherImageUrl;

    @Column(name = "program_image_url", length = 50)
    private String programImageUrl;

    @Column(name = "file_name", length = 200)
    private String fileName;

    @Column(name = "folder_name", length = 100)
    private String folderName;

    @Builder
    public ImageInfo2(String teacherImageUrl, String programImageUrl, String fileName, String folderName) {
        this.teacherImageUrl = teacherImageUrl;
        this.programImageUrl = programImageUrl;
        this.fileName = fileName;
        this.folderName = folderName;
    }
}

