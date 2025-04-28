package yeoun.like.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import yeoun.comment.domain.Comment;
import yeoun.user.domain.User;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@Table(name = "`like`")
@SQLDelete(sql = "UPDATE 'like' SET delete_time = CURRENT_TIMESTAMP WHERE id = ?") // soft delete
@SQLRestriction("delete_time IS NULL") // 지워지지 않은 레코드에 대한 조건
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Comment comment;

    @CreatedDate
    private LocalDateTime createdTime = LocalDateTime.now();

    @Column
    private LocalDateTime deleteTime;

    @Builder
    public Like(User user, Comment comment) {
        this.user = user;
        this.comment = comment;
    }
}
