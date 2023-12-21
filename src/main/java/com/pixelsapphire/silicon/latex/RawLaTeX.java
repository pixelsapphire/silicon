package com.pixelsapphire.silicon.latex;

import org.jetbrains.annotations.NotNull;

public class RawLaTeX implements LaTeX {

    private final String code;

    public RawLaTeX(@NotNull String code) {
        this.code = code;
    }

    @Override
    public @NotNull String translate() {
        return code;
    }
}
