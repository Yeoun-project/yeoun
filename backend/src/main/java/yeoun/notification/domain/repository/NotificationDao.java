package yeoun.notification.domain.repository;

import java.time.LocalDateTime;
import yeoun.question.domain.Question;

public interface NotificationDao {
    Question getQuestion();
    String getType();
    LocalDateTime getCreatedAt();
    Integer getCount();
    String getSender();
}
