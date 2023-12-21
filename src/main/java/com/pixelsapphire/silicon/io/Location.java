package com.pixelsapphire.silicon.io;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an element that is located in a source file or other text.
 */
public interface Location {

    /**
     * Sets the location of this element to the specified location.
     *
     * @param location the location of this token in the source file
     * @return this element
     */
    @Contract("_ -> this")
    @NotNull Location at(@NotNull FileCoordinates location);

    /**
     * Returns the location of the element in the source file (which will be {@code null}
     * if the location has not been set using {@link #at(FileCoordinates)}).
     *
     * @return the location of the element, or {@code null} if the location has not been set
     */
    @Nullable FileCoordinates getLocation();

    /**
     * Returns the location of the token in the source file, or {@link FileCoordinates#UNKNOWN}
     * if the location has not been set using {@link #at(FileCoordinates)}).
     *
     * @return the location of the token, or {@link FileCoordinates#UNKNOWN} if the location is {@code null}
     */
    default @NotNull FileCoordinates getLocationOrUnknown() {
        return getLocation() == null ? FileCoordinates.UNKNOWN : getLocation();
    }
}
