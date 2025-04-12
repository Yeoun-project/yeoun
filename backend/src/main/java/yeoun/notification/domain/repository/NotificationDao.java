package yeoun.notification.domain.repository;

import java.util.Date;
import yeoun.question.domain.Question;

public interface NotificationDao {
    Question getQuestion();
    String getType();
    Date getCreatedAt();
    Integer getCount();
    String getSender();
}
