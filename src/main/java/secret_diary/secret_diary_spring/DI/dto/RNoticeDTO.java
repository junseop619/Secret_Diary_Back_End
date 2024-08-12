package secret_diary.secret_diary_spring.DI.dto;

import lombok.*;

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

}
