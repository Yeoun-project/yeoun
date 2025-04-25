package yeoun.user.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "user_history")
@SQLDelete(sql = "UPDATE user SET user_history = CURRENT_TIMESTAMP WHERE id = ?") // soft delete
@SQLRestriction("delete_time IS NULL") // 지워지지 않은 레코드에 대한 조건
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.DETACH })
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @CreatedDate
    private final LocalDateTime createTime = LocalDateTime.now();

    @Column
    private LocalDateTime deleteTime;

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
