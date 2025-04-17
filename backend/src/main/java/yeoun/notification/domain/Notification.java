package yeoun.notification.domain;

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

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "notification")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private NotificationType notificationType;

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

    @CreatedDate
    private final LocalDateTime createdAt = LocalDateTime.now();

    @Builder
    public Notification(
            Long id, boolean isRead, NotificationType notificationType,
            User receiver, User sender, Question question
    ) {
        this.id = id;
        this.isRead = isRead;
        this.notificationType = notificationType;
        this.receiver = receiver;
        this.question = question;
        this.sender = sender;
    }
}
