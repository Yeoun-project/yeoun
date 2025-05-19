package yeoun.notification.service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public class SseService implements SseServiceInterface<Long, Integer> {

    @Value("${sse.connection_time}")
    private Long SSE_CONNECTION_TIME;
    private final Long SEE_RECONNECTION_TIME = 1000 * 2L;

    private static Map<Long, SseEmitter> emitterMap = new ConcurrentHashMap<>();

    @Override
    public SseEmitter createSseEmitter(Long pk, Integer firstData) {
        SseEmitter oldEmitter = emitterMap.get(pk);
        if(oldEmitter != null) {
            oldEmitter.complete();
            emitterMap.remove(pk);
        }

        SseEmitter emitter = new SseEmitter(SSE_CONNECTION_TIME);
        emitter.onTimeout(()->{ emitterMap.get(pk).complete(); emitterMap.remove(pk); });

        emitterMap.put(pk, emitter);

        sendData(pk, "connect", firstData);

        return emitter;
    }

    @Override
    public void sendData(Long pk, String title, Integer data) {
        SseEmitter emitter = emitterMap.get(pk);

        if(emitter == null)
            return;

        try {
            emitter.send(SseEmitter.event().name("notification").data(data.toString()).reconnectTime(SEE_RECONNECTION_TIME).build());
        } catch (IOException e) {
            ;
        }
    }

}
