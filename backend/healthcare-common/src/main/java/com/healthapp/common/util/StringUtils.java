package com.healthapp.common.util;

import java.security.SecureRandom;
import java.util.regex.Pattern;

/**
 * Utility class for string operations.
 */
public final class StringUtils {
    
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );
    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^\\+?[1-9]\\d{9,14}$"
    );
    
    private StringUtils() {}
    
    /**
     * Checks if a string is null or empty.
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }
    
    /**
     * Checks if a string is null, empty, or contains only whitespace.
     */
    public static boolean isBlank(String str) {
        return str == null || str.isBlank();
    }
    
    /**
     * Checks if a string is not blank.
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }
    
    /**
     * Validates an email address format.
     */
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
    
    /**
     * Validates a phone number format.
     */
    public static boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }
    
    /**
     * Generates a random numeric OTP of specified length.
     */
    public static String generateOtp(int length) {
        StringBuilder otp = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            otp.append(RANDOM.nextInt(10));
        }
        return otp.toString();
    }
    
    /**
     * Generates a random alphanumeric string of specified length.
     */
    public static String generateRandomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(RANDOM.nextInt(chars.length())));
        }
        return sb.toString();
    }
    
    /**
     * Masks an email address for display (e.g., "j***e@example.com").
     */
    public static String maskEmail(String email) {
        if (email == null || !email.contains("@")) {
            return email;
        }
        String[] parts = email.split("@");
        String name = parts[0];
        String domain = parts[1];
        
        if (name.length() <= 2) {
            return name.charAt(0) + "***@" + domain;
        }
        return name.charAt(0) + "***" + name.charAt(name.length() - 1) + "@" + domain;
    }
    
    /**
     * Masks a phone number for display (e.g., "+91******7890").
     */
    public static String maskPhone(String phone) {
        if (phone == null || phone.length() < 6) {
            return phone;
        }
        int visibleStart = phone.startsWith("+") ? 3 : 2;
        int visibleEnd = 4;
        
        return phone.substring(0, visibleStart) + 
               "*".repeat(phone.length() - visibleStart - visibleEnd) +
               phone.substring(phone.length() - visibleEnd);
    }
    
    /**
     * Capitalizes the first letter of each word.
     */
    public static String toTitleCase(String str) {
        if (isBlank(str)) {
            return str;
        }
        String[] words = str.toLowerCase().split("\\s+");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                if (result.length() > 0) {
                    result.append(" ");
                }
                result.append(Character.toUpperCase(word.charAt(0)))
                      .append(word.substring(1));
            }
        }
        return result.toString();
    }
    
    /**
     * Truncates a string to the specified length with ellipsis.
     */
    public static String truncate(String str, int maxLength) {
        if (str == null || str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength - 3) + "...";
    }
}
