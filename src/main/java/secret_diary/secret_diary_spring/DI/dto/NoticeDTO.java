package secret_diary.secret_diary_spring.DI.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import secret_diary.secret_diary_spring.DI.entity.Notice;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class NoticeDTO {


    private Long noticeId;
    private String userEmail;
    private String noticeTitle;
    private String noticeText;
    private MultipartFile noticeImg;

}
