package secret_diary.secret_diary_spring.DI.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "friend")
public class Friend {

    @Id
    @Column(name = "userEmail", updatable = false)
    private String userEmail;

    @Column(name = "friendEmail")
    private String friendEmail;

    @Builder
    public Friend(String userEmail, String friendEmail){
        this.userEmail = userEmail;
        this.friendEmail = friendEmail;
    }
}
