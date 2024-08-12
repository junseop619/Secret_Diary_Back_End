package secret_diary.secret_diary_spring.DI.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.Builder;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "imagefile")
public class FileInfo {
    @Id
    @Column(name = "fileName")
    String fileName;

    @Column(name = "saveFileName")
    String saveFileName;

    @Column(name = "contentType")
    String contentType;

    @Builder
    public FileInfo(String fileName, String saveFileName, String contentType){
        this.fileName=fileName;
        this.saveFileName=saveFileName;
        this.contentType=contentType;
    }

}
