package soongsil.kidbean.server.program.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import soongsil.kidbean.server.global.domain.S3Info;
import soongsil.kidbean.server.program.domain.type.ProgramCategory;
import soongsil.kidbean.server.program.dto.request.UpdateProgramRequest;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ProgramInfo {

    @Column(name = "program_title", length = 50)
    private String programTitle;

    @Column(name = "cotent_title", length = 50)
    private String contentTitle;

    @Column(name = "place", length = 50)
    private String place;

    @Column(name = "content", length = 1000)
    private String content;

    @Column(name = "program_category")
    @Enumerated(EnumType.STRING)
    private ProgramCategory programCategory;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "s3Url", column = @Column(name = "program_s3_url", length = 200)),
            @AttributeOverride(name = "fileName", column = @Column(name = "program_file_name", length = 200)),
            @AttributeOverride(name = "folderName", column = @Column(name = "program_folder_name", length = 100))
    })
    private S3Info programS3Info;

    @Builder
    public ProgramInfo(String programTitle, String contentTitle, String place, String content,
                       ProgramCategory programCategory, S3Info programS3Info) {
        this.programTitle = programTitle;
        this.contentTitle = contentTitle;
        this.place = place;
        this.content = content;
        this.programCategory = programCategory;
        this.programS3Info = programS3Info;
    }

    public void setProgramS3Info(S3Info programS3Info) {
        this.programS3Info = programS3Info;
    }

    public void updateProgram(UpdateProgramRequest updateProgramRequest) {
        this.programTitle = updateProgramRequest.programTitle();
        this.contentTitle = updateProgramRequest.contentTitle();
        this.place = updateProgramRequest.place();
        this.content = updateProgramRequest.content();
    }
}
