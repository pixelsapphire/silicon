package com.pixelsapphire.silicon.sicd.compiler;

import com.pixelsapphire.silicon.io.FileCoordinates;
import com.pixelsapphire.silicon.io.SiliconSourceException;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a generic exception that occurred during the compilation phase.
 */
public class CompilationException extends SiliconSourceException {

    /**
     * Constructs a new exception with the specified message and coordinates.
     * The coordinates are appended as a new line to the message.
     *
     * @param message     the message of the exception
     * @param coordinates the location of the exception cause
     */
    public CompilationException(@NotNull String message, @NotNull FileCoordinates coordinates) {
        super(message, coordinates);
    }

}
