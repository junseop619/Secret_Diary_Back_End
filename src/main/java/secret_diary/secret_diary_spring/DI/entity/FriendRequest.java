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
@Table(name = "friend_request")
public class FriendRequest {

    @Id
    @Column(name = "request", updatable = false)
    private String userEmail;

    @Column(name = "response")
    private String friendEmail;

    @Builder
    public FriendRequest(String userEmail, String friendEmail){
        this.userEmail = userEmail;
        this.friendEmail = friendEmail;
    }
}
