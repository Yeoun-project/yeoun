package yeoun.notification.domain;

import java.time.LocalDateTime;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
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
    private String content;

    @Column(nullable = false)
    private boolean isRead;

    @Column(nullable = false)
    private NotificationType notificationType;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;

    @CreatedDate
    private final Date sentDateTime = new Date();

    @Builder
    public Notification(Long id, String content, boolean isRead,
                        NotificationType notificationType, User receiver, Question question) {
        this.id = id;
        this.content = content;
        this.isRead = isRead;
        this.notificationType = notificationType;
        this.receiver = receiver;
        this.question = question;
    }
}
