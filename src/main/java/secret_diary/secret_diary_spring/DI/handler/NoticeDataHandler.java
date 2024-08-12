package secret_diary.secret_diary_spring.DI.handler;

import org.springframework.web.multipart.MultipartFile;
import secret_diary.secret_diary_spring.DI.entity.Notice;

import java.util.List;

public interface NoticeDataHandler {
    Notice saveNoticeEntity(String userEmail, String noticeTitle, String noticeText, String noticeImg);

    List<Notice> readAllNotice();
}
