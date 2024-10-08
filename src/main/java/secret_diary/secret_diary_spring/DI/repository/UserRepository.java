package secret_diary.secret_diary_spring.DI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.security.core.userdetails.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import secret_diary.secret_diary_spring.DI.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    User getByEmail(String email);

    User findUserByEmail(String email);

    Boolean existsByEmail(String email);


    //User findByEmailContaining(String keyword);

    List<User> findByEmailContainingAndEmailNot(String keyword, String userEmail);

    void deleteByEmail(String email);
}
