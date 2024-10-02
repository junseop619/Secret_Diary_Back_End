package secret_diary.secret_diary_spring.DI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import secret_diary.secret_diary_spring.DI.entity.Friend;

import java.util.List;
import java.util.Optional;


public interface FriendRepository extends JpaRepository<Friend, String> {
    List<Friend> findByUserEmail(String userEmail);

    List<Friend> findByFriendEmail(String userEmail);

    List<Friend> findByUserEmailOrFriendEmail(String userEmail, String friendEmail);

    List<Friend> findByUserEmailAndFriendEmailContaining(String userEmail, String friendEmail);

    Optional<Friend> findByUserEmailAndFriendEmail(String userEmail, String friendEmail);


}
