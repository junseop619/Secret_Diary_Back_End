package secret_diary.secret_diary_spring.DI.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;
import secret_diary.secret_diary_spring.DI.dto.NoticeDTO;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "notice")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "noticeId",updatable = false)
    Long noticeId;

    @Column(name = "userEmail")
    String userEmail;

    @Column(name = "noticeTitle")
    String noticeTitle;

    @Column(name = "noticeText")
    String noticeText;

    @Column(name = "noticeImg")
    String noticeImg;

    @Builder
    public Notice(String userEmail, String noticeTitle, String noticeText, String noticeImg){
        this.userEmail = userEmail;
        this.noticeTitle = noticeTitle;
        this.noticeText = noticeText;
        this.noticeImg = noticeImg;
    }

    /*
    public NoticeDTO toDto(){
        return NoticeDTO.builder()
                .noticeId(noticeId)
                .userEmail(userEmail)
                .noticeTitle(noticeTitle)
                .noticeText(noticeText)
                .noticeImg(noticeImg)
                .build();
    }*/
}
