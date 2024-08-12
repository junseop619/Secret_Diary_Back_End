package secret_diary.secret_diary_spring.service;

import org.springframework.web.multipart.MultipartFile;
import secret_diary.secret_diary_spring.DI.dto.NoticeDTO;
import secret_diary.secret_diary_spring.DI.dto.RNoticeDTO;

import java.util.List;

public interface NoticeService {
    NoticeDTO saveNotice(NoticeDTO dto);

    List<NoticeDTO> getReadAllNotice();

    List<RNoticeDTO> getReadAllNotice2();

    List<RNoticeDTO> getSearchNotice(String keyword);
}
