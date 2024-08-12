package secret_diary.secret_diary_spring.DI.handler;

import secret_diary.secret_diary_spring.DI.entity.User;

public interface UserDataHandler {
    User getUserEntity(String userId);
}
