package yeoun.question.domain;

import jakarta.persistence.*;
import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;
import org.springframework.data.annotation.CreatedDate;
import yeoun.comment.domain.Comment;
import yeoun.notification.domain.Notification;
import yeoun.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "question")
@Getter
@SQLDelete(sql = "UPDATE question SET delete_time = CURRENT_TIMESTAMP WHERE id = ?") // soft delete
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column
    private final boolean isFixed = false;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(targetEntity = Category.class, fetch = FetchType.EAGER)
    private Category category;

    @CreatedDate
    private final LocalDateTime createTime = LocalDateTime.now();

    @Column
    private LocalDateTime deleteTime;

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Notification> notifications;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<QuestionHistory> questionHistories;

    @Builder
    public Question(Long id, String content, User user, Category category) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.category = category;
    }
}
