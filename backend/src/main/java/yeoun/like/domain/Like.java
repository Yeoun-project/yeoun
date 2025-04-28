package yeoun.like.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import yeoun.comment.domain.Comment;
import yeoun.user.domain.User;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@Table(name = "`like`")
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

    @Builder
    public Like(User user, Comment comment) {
        this.user = user;
        this.comment = comment;
    }
}
