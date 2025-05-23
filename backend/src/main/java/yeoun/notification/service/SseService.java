package yeoun.notification.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@Slf4j
public class SseService implements SseServiceInterface<Long, Integer> {

    @Value("${sse.connection_time}")
    private Long SSE_CONNECTION_TIME;
    private final Long SEE_RECONNECTION_SECOND = 5L;

    private static Map<Long, CustomEmitter> emitterMap = new ConcurrentHashMap<>();

    @Override
    public SseEmitter getSseEmitter(Long pk, Integer firstData) {
        CustomEmitter oldEmitter = emitterMap.get(pk);
        if(oldEmitter != null && oldEmitter.wasRecentlyConnected()) {
            log.info("recently returned emitter");
            return null;
        }
        log.info("return new emitter");
        return createSseEmitter(pk, firstData);
    }

    private SseEmitter createSseEmitter(Long pk, Integer firstData) {
        SseEmitter emitter = new SseEmitter(SSE_CONNECTION_TIME);
        emitter.onTimeout(()->removeSseEmitter(pk));
        emitter.onCompletion(()->removeSseEmitter(pk));
        emitter.onError((e)->removeSseEmitter(pk));

        emitterMap.put(pk, new CustomEmitter(emitter));

        sendData(pk, "connect", firstData);

        log.info("emitter created");

        return emitter;
    }

    @Override
    public void sendData(Long pk, String title, Integer data) {
        CustomEmitter customEmitter = emitterMap.get(pk);

        if(customEmitter == null)
            return;

        try {
            customEmitter.getEmitter().send(SseEmitter.event().name("notification").data(data.toString()).reconnectTime(
                SEE_RECONNECTION_SECOND * 1000).build());
        } catch (IOException e) {
            log.info("sse send fail");
            removeSseEmitter(pk);
        }
    }

    private void removeSseEmitter(Long pk) {
        SseEmitter emitter = emitterMap.get(pk).getEmitter();
        try{
            emitter.complete();
        }catch(Exception e){
            ;
        }finally{
            emitterMap.remove(pk);
        }
    }

    @Getter @Setter
    class CustomEmitter {
        private LocalDateTime createdAt;
        private SseEmitter emitter;

        public boolean wasRecentlyConnected() {
            return createdAt.plusSeconds(SEE_RECONNECTION_SECOND).isAfter(LocalDateTime.now());
        }

        public CustomEmitter(SseEmitter emitter) {
            this.emitter = emitter;
            this.createdAt = LocalDateTime.now();
        }
    }

}
