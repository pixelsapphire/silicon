package com.pixelsapphire.silicon.latex;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface LaTeX {

    @Contract(pure = true)
    @NotNull String translate();

    @SuppressWarnings("unchecked")
    default <T extends LaTeXWrapper> @NotNull T wrappedWith(@NotNull T wrapper) {
        return (T) wrapper.wrap(this);
    }
}
