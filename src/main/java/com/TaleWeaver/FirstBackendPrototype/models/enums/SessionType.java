package com.TaleWeaver.FirstBackendPrototype.models.enums;

/**
 * Represents the different types of sessions.
 * Each session has its own duration.
 */
public enum SessionType {
    /** Session duration: 1 hour */
    SHORT(3600),
    /** Session duration: 1 day */
    MEDIUM(86400),
    /** Session duration: 1 week */
    LONG(604800);

    private final int expirationSeconds;

    SessionType(int expirationSeconds) {
        this.expirationSeconds = expirationSeconds;
    }

    public int getExpirationSeconds() {
        return expirationSeconds;
    }
}
