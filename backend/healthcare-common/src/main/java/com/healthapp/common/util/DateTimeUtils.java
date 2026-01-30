package com.healthapp.common.util;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for date/time operations.
 */
public final class DateTimeUtils {
    
    private static final ZoneId DEFAULT_ZONE = ZoneId.of("Asia/Kolkata");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    
    private DateTimeUtils() {}
    
    /**
     * Gets the current instant.
     */
    public static Instant now() {
        return Instant.now();
    }
    
    /**
     * Gets the current date in the default timezone.
     */
    public static LocalDate today() {
        return LocalDate.now(DEFAULT_ZONE);
    }
    
    /**
     * Gets the current time in the default timezone.
     */
    public static LocalTime currentTime() {
        return LocalTime.now(DEFAULT_ZONE);
    }
    
    /**
     * Gets the current date-time in the default timezone.
     */
    public static LocalDateTime currentDateTime() {
        return LocalDateTime.now(DEFAULT_ZONE);
    }
    
    /**
     * Converts an Instant to LocalDateTime in the default timezone.
     */
    public static LocalDateTime toLocalDateTime(Instant instant) {
        return instant.atZone(DEFAULT_ZONE).toLocalDateTime();
    }
    
    /**
     * Converts a LocalDateTime to Instant in the default timezone.
     */
    public static Instant toInstant(LocalDateTime dateTime) {
        return dateTime.atZone(DEFAULT_ZONE).toInstant();
    }
    
    /**
     * Formats a LocalDate as a string.
     */
    public static String formatDate(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }
    
    /**
     * Formats a LocalTime as a string.
     */
    public static String formatTime(LocalTime time) {
        return time.format(TIME_FORMATTER);
    }
    
    /**
     * Formats a LocalDateTime as a string.
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DATETIME_FORMATTER);
    }
    
    /**
     * Gets the start of day for a given date.
     */
    public static Instant startOfDay(LocalDate date) {
        return date.atStartOfDay(DEFAULT_ZONE).toInstant();
    }
    
    /**
     * Gets the end of day for a given date.
     */
    public static Instant endOfDay(LocalDate date) {
        return date.atTime(23, 59, 59, 999999999).atZone(DEFAULT_ZONE).toInstant();
    }
    
    /**
     * Checks if an instant is in the past.
     */
    public static boolean isPast(Instant instant) {
        return instant.isBefore(Instant.now());
    }
    
    /**
     * Checks if an instant is in the future.
     */
    public static boolean isFuture(Instant instant) {
        return instant.isAfter(Instant.now());
    }
    
    /**
     * Gets the default timezone.
     */
    public static ZoneId getDefaultZone() {
        return DEFAULT_ZONE;
    }
}
