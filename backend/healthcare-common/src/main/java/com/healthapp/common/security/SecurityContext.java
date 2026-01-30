package com.healthapp.common.security;

import reactor.core.publisher.Mono;
import reactor.util.context.Context;

/**
 * Reactive security context holder for accessing the current user.
 */
public final class SecurityContext {
    
    private static final String PRINCIPAL_KEY = "USER_PRINCIPAL";
    
    private SecurityContext() {}
    
    /**
     * Gets the current user principal from the reactive context.
     */
    public static Mono<UserPrincipal> getCurrentUser() {
        return Mono.deferContextual(ctx -> {
            if (ctx.hasKey(PRINCIPAL_KEY)) {
                return Mono.just(ctx.get(PRINCIPAL_KEY));
            }
            return Mono.empty();
        });
    }
    
    /**
     * Gets the current user ID from the reactive context.
     */
    public static Mono<String> getCurrentUserId() {
        return getCurrentUser().map(UserPrincipal::getUserId);
    }
    
    /**
     * Sets the user principal in the reactive context.
     */
    public static Context withUser(UserPrincipal principal) {
        return Context.of(PRINCIPAL_KEY, principal);
    }
    
    /**
     * Creates a context function to add the user principal.
     */
    public static java.util.function.Function<Context, Context> withUserFunction(UserPrincipal principal) {
        return ctx -> ctx.put(PRINCIPAL_KEY, principal);
    }
}
