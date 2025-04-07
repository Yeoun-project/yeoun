package yeoun.question.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import yeoun.user.domain.UserEntity;

import java.util.Date;

@Entity
@Table(name = "question_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    private final Date createdDateTime = new Date();

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity user;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private QuestionEntity question;

    @Column(columnDefinition = "varchar(500)")
    private String comment;

    @Builder
    public QuestionHistoryEntity(Long id, UserEntity user, QuestionEntity question, String comment) {
        this.id = id;
        this.user = user;
        this.question = question;
        this.comment = comment;
    }
}
