package yeoun.question.domain;

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

@Entity
@Table(name = "question_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String comment;

    @CreatedDate
    private final LocalDateTime createTime = LocalDateTime.now();

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Question question;

    @Builder
    public QuestionHistory(String comment, User user, Question question) {
        this.comment = comment;
        this.user = user;
        this.question = question;
    }
}
