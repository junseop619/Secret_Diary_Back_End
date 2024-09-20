package secret_diary.secret_diary_spring.DI.handler.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import secret_diary.secret_diary_spring.DI.dao.NoticeDAO;
import secret_diary.secret_diary_spring.DI.entity.Notice;
import secret_diary.secret_diary_spring.DI.handler.NoticeDataHandler;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoticeDataHandlerImpl implements NoticeDataHandler {

    @Autowired
    NoticeDAO noticeDAO;

    @Override
    public Notice saveNoticeEntity(String userEmail, String noticeTitle, String noticeText, String noticeImg, String date){
        Notice notice = new Notice(userEmail, noticeTitle, noticeText, noticeImg, date);
        return noticeDAO.saveNotice(notice);
    }

    @Override
    public List<Notice> readAllNotice(){
        return noticeDAO.readAllNotice();
    }
}
