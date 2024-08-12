package secret_diary.secret_diary_spring.DI.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import secret_diary.secret_diary_spring.DI.dao.NoticeDAO;
import secret_diary.secret_diary_spring.DI.entity.Notice;
import secret_diary.secret_diary_spring.DI.repository.NoticeRepository;

import java.util.List;

@Service
public class NoticeDAOImpl implements NoticeDAO {

    @Autowired
    NoticeRepository noticeRepository;

    @Override
    public Notice saveNotice(Notice notice){
        Notice notice1 = noticeRepository.save(notice);
        return notice1;
    }

    @Override
    public List<Notice> readAllNotice(){
        List<Notice> notice = noticeRepository.findAll();
        return notice;
    }



}
