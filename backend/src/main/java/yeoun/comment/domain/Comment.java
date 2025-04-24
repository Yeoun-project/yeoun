package yeoun.comment.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import yeoun.user.domain.User;
import yeoun.question.domain.Question;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@Getter
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

    @Builder
    public Comment(Long id, String content, User user, Question question) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.question = question;
    }

    public void updateAddLike() {
        this.likeCount = likeCount + 1;
    }

    public void updateRemoveLike() {
        this.likeCount = likeCount - 1;
    }
}
