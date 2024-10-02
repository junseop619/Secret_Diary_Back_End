package secret_diary.secret_diary_spring.DI.entity;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "requestId",updatable = false)
    private Long requestId;

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
