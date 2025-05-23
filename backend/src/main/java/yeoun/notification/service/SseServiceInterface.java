package yeoun.notification.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SseServiceInterface <T, D>{

    SseEmitter getSseEmitter(T pk, D firstData);

    void sendData(T pk, String title, D data);
}
