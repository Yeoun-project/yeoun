package yeoun.comment.domain;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import yeoun.like.domain.Like;
import yeoun.user.domain.User;
import yeoun.question.domain.Question;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "comment")
@Getter
@SQLDelete(sql = "UPDATE comment SET delete_time = CURRENT_TIMESTAMP WHERE id = ?") // soft delete
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Long likeCount = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;

    @BatchSize(size = 100)
    @OneToMany(targetEntity = Like.class, mappedBy = "comment", fetch = FetchType.LAZY, cascade = {})
    private List<Like> likes;

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

    public void updateAddLike() {
        this.likeCount = likeCount + 1;
    }

    public void updateRemoveLike() {
        this.likeCount = likeCount - 1;
    }
}
