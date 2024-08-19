package secret_diary.secret_diary_spring.DI.handler.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import secret_diary.secret_diary_spring.DI.dao.UserDAO;
import secret_diary.secret_diary_spring.DI.entity.User;
import secret_diary.secret_diary_spring.DI.handler.UserDataHandler;

@Service
public class UserDataHandlerImpl implements UserDataHandler {

    @Autowired
    UserDAO userDAO;


    @Override
    public User getUserEntity(String userId){
        return userDAO.getUser(userId);
    }
}
