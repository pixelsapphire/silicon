package com.pixelsapphire.silicon.latex;

import org.jetbrains.annotations.NotNull;

public abstract class LaTeXWrapper implements LaTeX {

    protected abstract @NotNull LaTeX wrap(@NotNull LaTeX latex);
}
