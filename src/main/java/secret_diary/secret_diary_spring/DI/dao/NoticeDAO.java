package secret_diary.secret_diary_spring.DI.dao;

import secret_diary.secret_diary_spring.DI.entity.Notice;

import java.util.List;

public interface NoticeDAO {
    Notice saveNotice(Notice notice);
    List<Notice> readAllNotice();

}
