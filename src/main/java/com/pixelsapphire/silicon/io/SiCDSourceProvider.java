package com.pixelsapphire.silicon.io;

import com.pixelsapphire.silicon.io.FileCoordinates;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface SiCDSourceProvider {

    @Contract(pure = true)
    @NotNull String getSource();

    /**
     * Returns the coordinates (line and column) of the character at the specified character position.
     *
     * @param characterIndex the character position
     * @return the coordinates of the character at the specified position
     */
    @Contract(pure = true)
    @NotNull FileCoordinates getCoordinates(int characterIndex);
}
