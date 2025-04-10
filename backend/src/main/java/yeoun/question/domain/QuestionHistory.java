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

    @Column(columnDefinition = "varchar(500)")
    private String comment;

    @Builder
<<<<<<< HEAD:backend/src/main/java/yeoun/question/domain/QuestionHistoryEntity.java
    public QuestionHistoryEntity(Long id, UserEntity user, QuestionEntity question, String comment) {
        this.id = id;
=======
    public QuestionHistory(String comment, User user, Question question) {
        this.comment = comment;
>>>>>>> d9428e8662699a05123a5a72f56aeffa81e9b6ca:backend/src/main/java/yeoun/question/domain/QuestionHistory.java
        this.user = user;
        this.question = question;
        this.comment = comment;
    }
}
