package secret_diary.secret_diary_spring.DI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import secret_diary.secret_diary_spring.DI.entity.FileInfo;

public interface FIleInfoRepository extends JpaRepository<FileInfo, String> {
}
