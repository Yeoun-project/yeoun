package yeoun.notification.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum NotificationType {

    COMMENT_LIKE("LIKE", "[%s]에 당신의 답변이 누군가에게 여운을 남겼어요! ❤", "[%s]에 당신의 답변이 ${<span className=\"text-[#FC90D1]\">n명</span>} 에게 여운을 남겼어요! ❤"),
    NEW_COMMENT("COMMENT", "[%s]에 새로운 답변이 달렸어요!", "[%s]에 새로운 답변이 ${<span className=\"text-[#FC90D1]\">n개</span>} 달렸어요!");

    public final String type;
    public final String[] content;

    NotificationType(String type, String... content) {
        this.type = type;
        this.content = content;
    }

    public static String getContent(String type, String questionContent, long count, String senderName) {
        if(count == 1)
            return String.format(NotificationType.valueOf(type).content[0], questionContent);
        else
            return String.format(NotificationType.valueOf(type).content[1], questionContent, count);
    }

}