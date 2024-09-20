package secret_diary.secret_diary_spring.DI.dto.Notice;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class RNoticeDTO {
    private Long noticeId;
    private String userEmail;
    private String noticeTitle;
    private String noticeText;
    private String noticeImgPath; // MultipartFile 대신 파일 경로를 저장
    //private LocalDateTime createdAt; //date update
    private String date;
}
