package com.healthapp.user.service;

import com.healthapp.common.exception.BusinessException;
import com.healthapp.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * Service for OTP generation, storage, and verification.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OtpService {
    
    private static final String OTP_KEY_PREFIX = "otp:";
    private static final String OTP_ATTEMPTS_PREFIX = "otp_attempts:";
    
    private final ReactiveRedisTemplate<String, Object> redisTemplate;
    
    @Value("${otp.length:6}")
    private int otpLength;
    
    @Value("${otp.expiry-minutes:10}")
    private int otpExpiryMinutes;
    
    @Value("${otp.max-attempts:3}")
    private int maxAttempts;
    
    /**
     * Generates and stores an OTP for the given identifier.
     */
    public Mono<String> generateOtp(String identifier) {
        String otp = StringUtils.generateOtp(otpLength);
        String key = OTP_KEY_PREFIX + identifier;
        
        return redisTemplate.opsForValue()
                .set(key, otp, Duration.ofMinutes(otpExpiryMinutes))
                .doOnSuccess(v -> log.debug("OTP generated for identifier: {}", StringUtils.maskEmail(identifier)))
                .thenReturn(otp);
    }
    
    /**
     * Verifies the OTP for the given identifier.
     */
    public Mono<Boolean> verifyOtp(String identifier, String otp) {
        String key = OTP_KEY_PREFIX + identifier;
        String attemptsKey = OTP_ATTEMPTS_PREFIX + identifier;
        
        return checkAttempts(attemptsKey)
                .flatMap(allowed -> {
                    if (!allowed) {
                        return Mono.error(new BusinessException(
                                "OTP_MAX_ATTEMPTS",
                                "Maximum OTP verification attempts exceeded. Please request a new OTP."
                        ));
                    }
                    
                    return redisTemplate.opsForValue().get(key)
                            .flatMap(storedOtp -> {
                                if (storedOtp != null && storedOtp.equals(otp)) {
                                    // OTP verified, delete it and reset attempts
                                    return redisTemplate.delete(key)
                                            .then(redisTemplate.delete(attemptsKey))
                                            .thenReturn(true);
                                } else {
                                    // Wrong OTP, increment attempts
                                    return incrementAttempts(attemptsKey)
                                            .thenReturn(false);
                                }
                            })
                            .switchIfEmpty(Mono.just(false)); // OTP expired or not found
                });
    }
    
    /**
     * Checks if verification attempts are still allowed.
     */
    private Mono<Boolean> checkAttempts(String attemptsKey) {
        return redisTemplate.opsForValue().get(attemptsKey)
                .map(attempts -> ((Number) attempts).intValue() < maxAttempts)
                .defaultIfEmpty(true);
    }
    
    /**
     * Increments the verification attempt counter.
     */
    private Mono<Long> incrementAttempts(String attemptsKey) {
        return redisTemplate.opsForValue().increment(attemptsKey)
                .flatMap(count -> {
                    if (count == 1) {
                        // Set expiry on first attempt
                        return redisTemplate.expire(attemptsKey, Duration.ofMinutes(otpExpiryMinutes))
                                .thenReturn(count);
                    }
                    return Mono.just(count);
                });
    }
    
    /**
     * Invalidates an existing OTP.
     */
    public Mono<Boolean> invalidateOtp(String identifier) {
        String key = OTP_KEY_PREFIX + identifier;
        return redisTemplate.delete(key).map(deleted -> deleted > 0);
    }
    
    /**
     * Sends OTP via SMS/Email (integration with notification service).
     * For now, this just logs the OTP. In production, this would call the notification service.
     */
    public Mono<Void> sendOtp(String identifier, String otp) {
        // TODO: Integrate with notification service
        log.info("OTP for {} is: {}", StringUtils.maskEmail(identifier), otp);
        return Mono.empty();
    }
}
