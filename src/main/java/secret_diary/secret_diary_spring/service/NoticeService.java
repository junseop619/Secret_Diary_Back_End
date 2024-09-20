package secret_diary.secret_diary_spring.service;

import secret_diary.secret_diary_spring.DI.dto.Notice.NoticeDTO;
import secret_diary.secret_diary_spring.DI.dto.Notice.RNoticeDTO;

import java.util.List;

public interface NoticeService {
    NoticeDTO saveNotice(NoticeDTO dto);


    List<NoticeDTO> getReadAllNotice();


    List<RNoticeDTO> getReadAllNotice2();

    List<RNoticeDTO> getReadUserNotice(String userEmail);

    List<RNoticeDTO> getSearchNotice(String keyword);

    RNoticeDTO getReadDetailNotice(Long noticeId);
}
