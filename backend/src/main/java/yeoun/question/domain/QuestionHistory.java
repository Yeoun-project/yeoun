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
import yeoun.user.domain.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "question_history")
@Getter
@SQLDelete(sql = "UPDATE question_history SET delete_time = CURRENT_TIMESTAMP WHERE id = ?") // soft delete
@SQLRestriction("delete_time IS NULL") // 지워지지 않은 레코드에 대한 조건
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(500)")
    private String comment;

    @Column
    private LocalDateTime commentTime;

    @CreatedDate
    private final LocalDateTime createTime = LocalDateTime.now();

    @Column
    private LocalDateTime deleteTime;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Question question;

    @Builder
    public QuestionHistory(Long id, User user, Question question, String comment) {
        this.id = id;
        this.user = user;
        this.question = question;
        this.comment = comment;
    }
}
