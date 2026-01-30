package com.healthapp.user.event;

import com.healthapp.common.event.BaseEvent;
import com.healthapp.common.event.EventTypes;
import com.healthapp.common.event.KafkaTopics;
import com.healthapp.user.model.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

import java.util.Map;

/**
 * Publisher for user-related domain events.
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "spring.kafka.enabled", havingValue = "true", matchIfMissing = true)
public class UserEventPublisher {
    
    private static final String SOURCE = "user-service";
    
    private final KafkaSender<String, Object> kafkaSender;
    
    /**
     * Publishes a user.registered event.
     */
    public void publishUserRegistered(User user) {
        publishEvent(EventTypes.USER_REGISTERED, user.getId().toString(), Map.of(
                "userId", user.getId().toString(),
                "email", user.getEmail() != null ? user.getEmail() : "",
                "phone", user.getPhone() != null ? user.getPhone() : "",
                "role", user.getRole().name(),
                "firstName", user.getFirstName(),
                "lastName", user.getLastName()
        ));
    }
    
    /**
     * Publishes a user.verified event.
     */
    public void publishUserVerified(User user) {
        publishEvent(EventTypes.USER_VERIFIED, user.getId().toString(), Map.of(
                "userId", user.getId().toString(),
                "email", user.getEmail() != null ? user.getEmail() : "",
                "phone", user.getPhone() != null ? user.getPhone() : "",
                "role", user.getRole().name()
        ));
    }
    
    /**
     * Publishes a user.updated event.
     */
    public void publishUserUpdated(User user) {
        publishEvent(EventTypes.USER_UPDATED, user.getId().toString(), Map.of(
                "userId", user.getId().toString(),
                "displayName", user.getFullName(),
                "avatarUrl", user.getAvatarUrl() != null ? user.getAvatarUrl() : ""
        ));
    }
    
    /**
     * Publishes a user.login event.
     */
    public void publishUserLogin(User user) {
        publishEvent(EventTypes.USER_LOGIN, user.getId().toString(), Map.of(
                "userId", user.getId().toString(),
                "loginTime", user.getLastLoginAt().toString()
        ));
    }
    
    private void publishEvent(String eventType, String key, Map<String, Object> data) {
        BaseEvent<Map<String, Object>> event = BaseEvent.of(eventType, SOURCE, data);
        
        ProducerRecord<String, Object> record = new ProducerRecord<>(
                KafkaTopics.USER_EVENTS,
                key,
                event
        );
        
        kafkaSender.send(Mono.just(SenderRecord.create(record, key)))
                .doOnNext(result -> log.debug("Published event: {} for key: {}", eventType, key))
                .doOnError(error -> log.error("Failed to publish event: {} for key: {}", eventType, key, error))
                .subscribe();
    }
}
