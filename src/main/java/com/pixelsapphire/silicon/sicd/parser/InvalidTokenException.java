package com.pixelsapphire.silicon.sicd.parser;

import com.pixelsapphire.silicon.io.FileCoordinates;
import com.pixelsapphire.silicon.io.SiliconSourceException;
import org.jetbrains.annotations.NotNull;

/**
 * Signals that an invalid token has been encountered during parsing.
 */
@SuppressWarnings("unused")
public class InvalidTokenException extends SiliconSourceException {

    /**
     * Constructs a new exception with the specified message and coordinates.
     * The coordinates are appended as a new line to the message.
     *
     * @param message     the message of the exception
     * @param coordinates the location of the exception cause
     */
    public InvalidTokenException(@NotNull String message, @NotNull FileCoordinates coordinates) {
        super(message, coordinates);
    }
}
