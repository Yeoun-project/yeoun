package yeoun.notification.domain;

import java.time.LocalDateTime;
import yeoun.question.domain.Question;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import yeoun.user.domain.User;

@Entity
@Table(name = "notification")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String notificationType;

    @Column(nullable = false)
    private boolean isRead;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User receiver;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User sender;

    @ManyToOne(fetch = FetchType.EAGER)
    private Question question;

    @Column(nullable = false)
    private Long count = 1L;

    @CreatedDate
    private final LocalDateTime createTime = LocalDateTime.now();

    @Builder
    public Notification(Long id, String content, boolean isRead,
        NotificationType notificationType, User receiver, User sender, Question question) {
        this.id = id;
        this.isRead = isRead;
        this.notificationType = notificationType.toString();
        this.receiver = receiver;
        this.question = question;
        this.sender = sender;
    }

}