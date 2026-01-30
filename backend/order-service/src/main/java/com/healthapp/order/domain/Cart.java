package com.healthapp.order.domain;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Cart entity stored in Redis for fast access.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart implements Serializable {

    private String userId;
    
    @Builder.Default
    private List<CartItem> items = new ArrayList<>();
    
    private String couponCode;
    
    @Builder.Default
    private BigDecimal discountAmount = BigDecimal.ZERO;
    
    private Instant createdAt;
    
    private Instant updatedAt;

    public static Cart empty(String userId) {
        return Cart.builder()
                .userId(userId)
                .items(new ArrayList<>())
                .discountAmount(BigDecimal.ZERO)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }

    public Cart addItem(CartItem item) {
        Optional<CartItem> existing = items.stream()
                .filter(i -> i.getProductId().equals(item.getProductId()))
                .findFirst();

        if (existing.isPresent()) {
            CartItem existingItem = existing.get();
            existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
        } else {
            items.add(item);
        }
        this.updatedAt = Instant.now();
        return this;
    }

    public Cart addItems(List<CartItem> newItems) {
        newItems.forEach(this::addItem);
        return this;
    }

    public Cart updateQuantity(String itemId, int quantity) {
        items.stream()
                .filter(i -> i.getItemId().equals(itemId))
                .findFirst()
                .ifPresent(item -> item.setQuantity(quantity));
        this.updatedAt = Instant.now();
        return this;
    }

    public Cart removeItem(String itemId) {
        items.removeIf(i -> i.getItemId().equals(itemId));
        this.updatedAt = Instant.now();
        return this;
    }

    public Cart applyCoupon(String code, BigDecimal discount) {
        this.couponCode = code;
        this.discountAmount = discount;
        this.updatedAt = Instant.now();
        return this;
    }

    public Cart removeCoupon() {
        this.couponCode = null;
        this.discountAmount = BigDecimal.ZERO;
        this.updatedAt = Instant.now();
        return this;
    }

    public BigDecimal getSubtotal() {
        return items.stream()
                .map(CartItem::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public int getTotalItems() {
        return items.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

    public int getItemCount() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public boolean hasItem(String productId) {
        return items.stream()
                .anyMatch(i -> i.getProductId().equals(productId));
    }
}
