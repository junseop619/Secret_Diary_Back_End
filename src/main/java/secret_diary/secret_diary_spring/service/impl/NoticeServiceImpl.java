package secret_diary.secret_diary_spring.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import secret_diary.secret_diary_spring.DI.dto.Notice.NoticeDTO;
import secret_diary.secret_diary_spring.DI.dto.Notice.RNoticeDTO;
import secret_diary.secret_diary_spring.DI.entity.Notice;
import secret_diary.secret_diary_spring.DI.handler.NoticeDataHandler;
import secret_diary.secret_diary_spring.DI.repository.NoticeRepository;
import secret_diary.secret_diary_spring.service.NoticeService;
import org.springframework.mock.web.MockMultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Transactional
@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService {



    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    NoticeDataHandler noticeDataHandler;

    @Autowired
    NoticeRepository noticeRepository;

    @Value("${upload.path}")
    String uploadPath;

    @Override
    public NoticeDTO saveNotice(NoticeDTO dto){

        MultipartFile file = dto.getNoticeImg();

        if (file == null) {
            logger.info("No file provided.");
            //throw new IllegalArgumentException("File must not be null");
        }

        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null) {
            logger.info("Original file name is null.");
            //throw new IllegalArgumentException("File name must not be null");
        }

        String saveFileName = createSaveFileName(originalFileName);


        try {
            //내 컴퓨터에 파일을 저장함
            dto.getNoticeImg().transferTo(new File(getFullPath(saveFileName)));

        } catch (IOException e){
            logger.info("service failed");
            e.printStackTrace();
            throw new RuntimeException("Failed to save file", e);
        }

        String contentType = dto.getNoticeImg().getContentType();

        Notice notice = noticeDataHandler.saveNoticeEntity(dto.getUserEmail(), dto.getNoticeTitle(), dto.getNoticeText(), saveFileName, dto.getDate());
        NoticeDTO noticeDTO = new NoticeDTO(notice.getNoticeId(), notice.getUserEmail(), notice.getNoticeTitle(), notice.getNoticeText(), dto.getNoticeImg(), notice.getDate());

        return noticeDTO;
    }

    @Override
    public List<NoticeDTO> getReadAllNotice(){
        //List<Notice> entities = noticeDataHandler.readAllNotice();
        List<Notice> entities = noticeRepository.findAll();
        List<NoticeDTO> dtos = new ArrayList<>();

        for(Notice entity : entities){
            NoticeDTO noticeDTO = new NoticeDTO();

            MultipartFile multipartFile= null;
            String saveFileName =entity.getNoticeImg();


            String file_name = saveFileName;
            String file_path = "/Users/hwangjunseob/Desktop/android_portpolio/img_sever/" + saveFileName;
            File file = new File("/Users/hwangjunseob/Desktop/android_portpolio/img_sever/" + saveFileName);

            try {
                multipartFile = convertFileToMultipartFile(file,file_name,file_path);
                logger.info("File name: " + multipartFile.getOriginalFilename());
                logger.info("File size: " + multipartFile.getSize());
            } catch (IOException e){
                logger.error("Error converting file to MultipartFile", e);
                e.printStackTrace();
            }

            noticeDTO.setUserEmail(entity.getUserEmail());
            noticeDTO.setNoticeTitle(entity.getNoticeTitle());
            noticeDTO.setNoticeText(entity.getNoticeText());
            noticeDTO.setNoticeImg(multipartFile);
            //noticeDTO.setCreatedAt(entity.getCreatedAt()); //date update
            noticeDTO.setDate(entity.getDate());
            dtos.add(noticeDTO);
        }
        return dtos;
    }


    @Override
    public List<RNoticeDTO> getReadAllNotice2(){
        List<Notice> entities = noticeRepository.findAll();
        List<RNoticeDTO> dtos = new ArrayList<>();

        for (Notice entity : entities) {
            RNoticeDTO noticeDTO_R = new RNoticeDTO();

            noticeDTO_R.setNoticeId(entity.getNoticeId()); //test
            noticeDTO_R.setUserEmail(entity.getUserEmail());
            noticeDTO_R.setNoticeTitle(entity.getNoticeTitle());
            noticeDTO_R.setNoticeText(entity.getNoticeText());
            noticeDTO_R.setNoticeImgPath(entity.getNoticeImg());
            //noticeDTO_R.setCreatedAt(entity.getCreatedAt()); //date update
            noticeDTO_R.setDate(entity.getDate());
            dtos.add(noticeDTO_R);
        }
        return dtos;
    }

    @Override
    public List<RNoticeDTO> getReadUserNotice(String userEmail){
        //List<Notice> entities = noticeRepository.findByUserEmail(userEmail);
        String formattedEmail = "\"" + userEmail + "\"";

        // 큰따옴표가 포함된 email로 검색
        List<Notice> entities = noticeRepository.findByUserEmail(formattedEmail);
        logger.info("Searching notices for userEmail: " + userEmail);
        logger.info("Number of notices found: " + entities.size());

        List<RNoticeDTO> dtos = new ArrayList<>();

        for (Notice entity : entities) {
            RNoticeDTO noticeDTO_R = new RNoticeDTO();

            noticeDTO_R.setNoticeId(entity.getNoticeId()); //test
            noticeDTO_R.setUserEmail(entity.getUserEmail());
            noticeDTO_R.setNoticeTitle(entity.getNoticeTitle());
            logger.info("load user notice: " + noticeDTO_R.getNoticeTitle());
            noticeDTO_R.setNoticeText(entity.getNoticeText());
            noticeDTO_R.setNoticeImgPath(entity.getNoticeImg());
            //noticeDTO_R.setCreatedAt(entity.getCreatedAt()); //date update
            noticeDTO_R.setDate(entity.getDate());
            //logger.info("***********date update********: " + noticeDTO_R.getCreatedAt());
            logger.info("***********date update********: " + noticeDTO_R.getDate());
            dtos.add(noticeDTO_R);
        }
        return dtos;
    }

    @Override
    public RNoticeDTO getReadDetailNotice(Long noticeId){
        Notice entities = noticeRepository.findByNoticeId(noticeId);
        RNoticeDTO dtos =  new RNoticeDTO();

        dtos.setNoticeId(entities.getNoticeId());
        dtos.setUserEmail(entities.getUserEmail());
        dtos.setNoticeTitle(entities.getNoticeTitle());
        dtos.setNoticeText(entities.getNoticeText());
        dtos.setNoticeImgPath(entities.getNoticeImg());
        //dtos.setCreatedAt(entities.getCreatedAt());
        dtos.setDate(entities.getDate());
        return dtos;
    }

    @Override
    public List<RNoticeDTO> getSearchNotice(String keyword){
        List<Notice> entities = noticeRepository.findByNoticeTitleContaining(keyword);
        List<RNoticeDTO> dtos = new ArrayList<>();

        for (Notice entity : entities) {
            RNoticeDTO noticeDTO_R = new RNoticeDTO();

            noticeDTO_R.setNoticeId(entity.getNoticeId()); //test
            noticeDTO_R.setUserEmail(entity.getUserEmail());
            noticeDTO_R.setNoticeTitle(entity.getNoticeTitle());
            noticeDTO_R.setNoticeText(entity.getNoticeText());
            noticeDTO_R.setNoticeImgPath(entity.getNoticeImg());
            //noticeDTO_R.setCreatedAt(entity.getCreatedAt());
            noticeDTO_R.setDate(entity.getDate());
            dtos.add(noticeDTO_R);
        }
        return dtos;

    }

    private String createSaveFileName(String originalFileName){
        String ext = extractExt(originalFileName);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFileName){
        int pos = originalFileName.lastIndexOf(".");
        return originalFileName.substring(pos+1);
    }

    private String getFullPath(String fileName){
        return uploadPath + fileName;
    }

    private MultipartFile convertFileToMultipartFile(File file, String file_name, String file_path) throws  IOException {
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(file_name, new FileInputStream(new File(file_path)));

        return multipartFile;
    }
}
