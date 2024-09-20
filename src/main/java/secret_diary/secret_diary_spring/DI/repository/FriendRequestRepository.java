package secret_diary.secret_diary_spring.DI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import secret_diary.secret_diary_spring.DI.entity.Friend;
import secret_diary.secret_diary_spring.DI.entity.FriendRequest;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, String> {
    List<FriendRequest> findByUserEmail(String email);

    List<FriendRequest> findByFriendEmail(String email);

    void deleteByUserEmailAndFriendEmail(String userEmail, String friendEmail);

    Optional<FriendRequest> findByUserEmailAndFriendEmail(String userEmail, String friendEmail);
}
