package secret_diary.secret_diary_spring.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import secret_diary.secret_diary_spring.DI.dto.NoticeDTO;
import secret_diary.secret_diary_spring.DI.dto.RNoticeDTO;
import secret_diary.secret_diary_spring.service.NoticeService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RequiredArgsConstructor
//@Controller
@RestController
public class NoticeController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    NoticeService noticeService;

    @Value("${upload.path}")
    String uploadPath;

    @PostMapping("upload")
    public ResponseEntity<String> uploadFile(
            @RequestPart("noticeImg") MultipartFile file,
            @RequestPart("userEmail") String userEmail,
            @RequestPart("noticeTitle") String title,
            @RequestPart("noticeText") String text) {

        if (file.isEmpty()) {
            return new ResponseEntity<>("File is empty", HttpStatus.BAD_REQUEST);
        }

        try {
            String fileName = file.getOriginalFilename();
            // 파일 처리 로직 추가
            NoticeDTO noticeDTO = new NoticeDTO();
            noticeDTO.setUserEmail(userEmail);
            noticeDTO.setNoticeTitle(title);
            noticeDTO.setNoticeText(text);
            noticeDTO.setNoticeImg(file);

            NoticeDTO response = noticeService.saveNotice(noticeDTO);
            return new ResponseEntity<>("File uploaded successfully: " + fileName, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("notice/upload")
    public ResponseEntity<NoticeDTO> writeNotice(@Validated @RequestBody NoticeDTO noticeDTO){
        NoticeDTO response = noticeService.saveNotice(noticeDTO);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping("findAll")
    public List<NoticeDTO> readAllNotice(){
        return noticeService.getReadAllNotice();
    }


    @GetMapping("findAll2")
    public List<RNoticeDTO> readAllNotice2(){
        return noticeService.getReadAllNotice2();
    }

    @GetMapping("search/notice")
    public List<RNoticeDTO> searchNotice(@RequestParam("keyword") String keyword){
        return noticeService.getSearchNotice(keyword);
    }

    /*@GetMapping("/notice/image/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(uploadPath).resolve(filename);
            Resource resource = new UrlResource(filePath.toUri());

            logger.debug("Requested filename: " + filename);
            logger.debug("Resolved file path: " + filePath.toString());

            if (!resource.exists() || !resource.isReadable()) {
                throw new RuntimeException("Could not read the file!");
            }

            // Determine the content type
            String contentType = "application/octet-stream";
            try {
                contentType = Files.probeContentType(filePath);
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }
            } catch (IOException e) {
                logger.error("Could not determine file type.");
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }*/

    @GetMapping("/notice/image/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable("filename") String filename) {
        try {
            Path filePath = Paths.get(uploadPath).resolve(filename);
            Resource resource = new UrlResource(filePath.toUri());

            logger.debug("Requested filename: " + filename);
            logger.debug("Resolved file path: " + filePath.toString());

            if (!resource.exists() || !resource.isReadable()) {
                throw new RuntimeException("Could not read the file!");
            }

            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (MalformedURLException e) {
            logger.error("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (IOException e) {
            logger.error("Could not determine file type.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }





}
