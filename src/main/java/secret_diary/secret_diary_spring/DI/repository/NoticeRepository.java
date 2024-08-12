package secret_diary.secret_diary_spring.DI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import secret_diary.secret_diary_spring.DI.entity.Notice;
import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    //@Query("SELECT n FROM Notice n WHERE n.title LIKE %:keyword%")
    List<Notice> findByNoticeTitleContaining(String keyword);
}
