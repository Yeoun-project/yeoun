package yeoun.comment.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import yeoun.user.domain.User;
import yeoun.question.domain.Question;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@Getter
@SQLDelete(sql = "UPDATE comment SET delete_time = CURRENT_TIMESTAMP WHERE id = ?") // soft delete
@SQLRestriction("delete_time IS NULL") // 지워지지 않은 레코드에 대한 조건
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Long likeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Question question;

    @CreatedDate
    private final LocalDateTime createTime = LocalDateTime.now();

    @Column
    private LocalDateTime deleteTime;

    @Builder
    public Comment(Long id, String content, User user, Question question) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.question = question;
    }
}
