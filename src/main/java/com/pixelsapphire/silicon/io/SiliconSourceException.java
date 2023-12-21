package com.pixelsapphire.silicon.io;

import org.jetbrains.annotations.NotNull;

/**
 * Represents an exception that occurred while processing a SiCD source file.
 */
public abstract class SiliconSourceException extends RuntimeException {

    /**
     * Constructs a new exception with the specified message and coordinates.
     * The coordinates are appended as a new line to the message.
     *
     * @param message     the message of the exception
     * @param coordinates the location of the exception cause
     */
    protected SiliconSourceException(@NotNull String message, @NotNull FileCoordinates coordinates) {
        super(message + "\nat " + coordinates);
    }
}
