package yeoun.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "user_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @LastModifiedDate
    @Column(name = "last_login", nullable = false)
    private Date lastLogin= new Date();

    @Column(nullable = false)
    private String ip;

    @Column(nullable = false)
    private String agent;

    @Column(nullable = true)
    private String uri;

    @Builder
    public UserHistory(User user, String ip, String agent,
                       String uri) {
        this.user = user;
        this.ip = ip;
        this.agent = agent;
        this.uri = uri;
    }
}
