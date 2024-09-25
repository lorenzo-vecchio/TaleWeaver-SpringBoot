package com.TaleWeaver.FirstBackendPrototype.models.enums;

/**
 * Represents the different types of sessions.
 * Each session has its own duration.
 */
public enum SessionType {
    /** Session duration: 1 hour */
    HOUR(3600),
    /** Session duration: 1 day */
    DAY(86400),
    /** Session duration: 1 week */
    WEEK(604800);

    private final int expirationSeconds;

    SessionType(int expirationSeconds) {
        this.expirationSeconds = expirationSeconds;
    }

    public int getExpirationSeconds() {
        return expirationSeconds;
    }
}
