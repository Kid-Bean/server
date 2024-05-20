package soongsil.kidbean.server.program.domain.vo;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import soongsil.kidbean.server.global.domain.S3Info;
import soongsil.kidbean.server.program.dto.request.UpdateProgramRequest;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class DepartmentInfo {

    @Column(name = "department_name", length = 30)
    private String departmentName;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "s3Url", column = @Column(name = "department_s3_url", length = 200)),
            @AttributeOverride(name = "fileName", column = @Column(name = "department_file_name", length = 200)),
            @AttributeOverride(name = "folderName", column = @Column(name = "department_folder_name", length = 100))
    })
    private S3Info departmentS3Info;

    @Builder
    public DepartmentInfo(String departmentName, String phoneNumber, S3Info departmentS3Info) {
        this.departmentName = departmentName;
        this.phoneNumber = phoneNumber;
        this.departmentS3Info = departmentS3Info;
    }

    public void setDepartmentS3Info(S3Info departmentS3Info) {
        this.departmentS3Info = departmentS3Info;
    }

    public void updateDepartment(UpdateProgramRequest updateProgramRequest) {
        this.departmentName = updateProgramRequest.departmentName();
        this.phoneNumber = updateProgramRequest.phoneNumber();
    }
}
