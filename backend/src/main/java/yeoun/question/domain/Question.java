package yeoun.question.domain;

import jakarta.persistence.*;
import java.util.Date;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import yeoun.comment.domain.Comment;
import yeoun.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "question")
@Getter
@SQLDelete(sql = "UPDATE question SET delete_time = CURRENT_TIMESTAMP WHERE id = ?") // soft delete
@SQLRestriction("delete_time IS NULL") // 지워지지 않은 레코드에 대한 조건
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column
    private final boolean isFixed = false;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Category category;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Comment> comments;

    @CreatedDate
    private final Date createTime = new Date();

    @Column
    private Date deleteTime;

    @Builder
    public Question(Long id, String content, User user, Category category) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.category = category;
    }
}
