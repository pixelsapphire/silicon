package com.pixelsapphire.silicon.io;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a pair of 1-indexed coordinates in a file. The file path may be {@code null},
 * in which case {@link #toString()} will show {@code <source>} as the file path.
 *
 * @param file   the file path
 * @param line   the line number
 * @param column the column number
 */
public record FileCoordinates(@Nullable String file, int line, int column) {

    public static final FileCoordinates UNKNOWN = new FileCoordinates("<unknown>", 1, 1);

    /**
     * Constructs a new file coordinates object with the specified file path, line number and column number.
     *
     * @param file   the file path
     * @param line   the line number
     * @param column the column number
     * @throws IllegalArgumentException if the line or column number is not positive
     */
    public FileCoordinates {
        if (line < 1) throw new IllegalArgumentException("The line number must be positive");
        if (column < 1) throw new IllegalArgumentException("The column number must be positive");
    }

    /**
     * Returns a string representation of this file coordinates object in the format: {@code <file>:<line>:<column>}.
     * If the location is {@link #UNKNOWN}, the string {@code <unknown>} is returned.
     *
     * @return the string representation of this coordinates
     */
    @Override
    @Contract(pure = true)
    public @NotNull String toString() {
        if (this == UNKNOWN) return "<unknown>";
        return (file == null ? "<source>" : file) + ":" + line + ":" + column;
    }
}
