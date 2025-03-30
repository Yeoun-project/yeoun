package yeoun.notification.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {

    QUESTION_LIKE("QUESTION_LIKE", "당신의 답변이 누군가에게 여운을 남겼어요! ❤"),
    COMMENT("COMMENT", "[%s]에 새로운 답변이 달렸어요!");

    public final String type;
    public final String content;

    public String getContent(String args) {
        if(this.equals(NotificationType.QUESTION_LIKE)) {
            return content;
        }
        else { // if equals Comment
            return String.format(content, args);
        }
    }

}