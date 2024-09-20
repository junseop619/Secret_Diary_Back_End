package secret_diary.secret_diary_spring.DI.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.Builder;

import java.time.LocalDateTime;


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

    /*
    @Column(name = "date")
    LocalDateTime createdAt;
     */

    @Column(name = "date")
    String date;

    @Builder
    public Notice(String userEmail, String noticeTitle, String noticeText, String noticeImg, String date){
        this.userEmail = userEmail;
        this.noticeTitle = noticeTitle;
        this.noticeText = noticeText;
        this.noticeImg = noticeImg;
        this.date = date;
        //this.createdAt = createdAt;
    }

}
