package secret_diary.secret_diary_spring.DI.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import secret_diary.secret_diary_spring.DI.dao.UserDAO;
import secret_diary.secret_diary_spring.DI.entity.User;
import secret_diary.secret_diary_spring.DI.repository.UserRepository;

@Service
public class UserDAOImpl implements UserDAO {

    @Autowired
    UserRepository userRepository;

    @Override
    public User getUser(String userId){
        User user = userRepository.getByEmail(userId);
        return user;
    }
}
