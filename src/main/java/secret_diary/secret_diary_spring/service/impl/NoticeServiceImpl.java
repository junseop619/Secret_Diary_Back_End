package secret_diary.secret_diary_spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import secret_diary.secret_diary_spring.DI.dto.NoticeDTO;
import secret_diary.secret_diary_spring.DI.dto.RNoticeDTO;
import secret_diary.secret_diary_spring.DI.entity.FileInfo;
import secret_diary.secret_diary_spring.DI.entity.Notice;
import secret_diary.secret_diary_spring.DI.handler.NoticeDataHandler;
import secret_diary.secret_diary_spring.DI.repository.NoticeRepository;
import secret_diary.secret_diary_spring.service.NoticeService;
import org.springframework.mock.web.MockMultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


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

        Notice notice = noticeDataHandler.saveNoticeEntity(dto.getUserEmail(), dto.getNoticeTitle(), dto.getNoticeText(), saveFileName);
        NoticeDTO noticeDTO = new NoticeDTO(notice.getNoticeId(), notice.getUserEmail(), notice.getNoticeTitle(), notice.getNoticeText(), dto.getNoticeImg());

        return noticeDTO;
    }

<<<<<<< HEAD
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
            dtos.add(noticeDTO);
        }
        return dtos;
    }
=======
>>>>>>> 37fbecc (08/18 update)

    @Override
    public List<RNoticeDTO> getReadAllNotice2(){
        List<Notice> entities = noticeRepository.findAll();
        List<RNoticeDTO> dtos = new ArrayList<>();

        for (Notice entity : entities) {
            RNoticeDTO noticeDTO_R = new RNoticeDTO();
<<<<<<< HEAD
=======
            noticeDTO_R.setNoticeId(entity.getNoticeId()); //test
>>>>>>> 37fbecc (08/18 update)
            noticeDTO_R.setUserEmail(entity.getUserEmail());
            noticeDTO_R.setNoticeTitle(entity.getNoticeTitle());
            noticeDTO_R.setNoticeText(entity.getNoticeText());
            noticeDTO_R.setNoticeImgPath(entity.getNoticeImg());
            dtos.add(noticeDTO_R);
        }
        return dtos;
    }

    @Override
    public List<RNoticeDTO> getSearchNotice(String keyword){
        List<Notice> entities = noticeRepository.findByNoticeTitleContaining(keyword);
        List<RNoticeDTO> dtos = new ArrayList<>();

        for (Notice entity : entities) {
            RNoticeDTO noticeDTO_R = new RNoticeDTO();
<<<<<<< HEAD
=======
            noticeDTO_R.setNoticeId(entity.getNoticeId()); //test
>>>>>>> 37fbecc (08/18 update)
            noticeDTO_R.setUserEmail(entity.getUserEmail());
            noticeDTO_R.setNoticeTitle(entity.getNoticeTitle());
            noticeDTO_R.setNoticeText(entity.getNoticeText());
            noticeDTO_R.setNoticeImgPath(entity.getNoticeImg());
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
