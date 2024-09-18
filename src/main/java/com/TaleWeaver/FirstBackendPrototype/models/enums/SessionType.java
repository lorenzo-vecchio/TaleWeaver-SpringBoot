package com.TaleWeaver.FirstBackendPrototype.models.enums;

/**
 * Represents the different types of sessions.
 * Each session has its own duration.
 */
public enum SessionType {
    /**
     * Short session - typically for brief activities. Duration = 1 hr / 3600 s
     */
    SHORT,
    /**
     * Long session - for extended activities. Duration = 1 month / 2592000 s
     */
    LONG,
    /**
     * Permanent session - no pre-defined end time.
     */
    PERMANENT
}
