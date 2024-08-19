package secret_diary.secret_diary_spring.DI.dao;

import secret_diary.secret_diary_spring.DI.entity.User;

public interface UserDAO {

    User getUser(String userId);
}
