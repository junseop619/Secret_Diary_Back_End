package secret_diary.secret_diary_spring.DI.entity;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relationId",updatable = false)
    private Long relationId;

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
