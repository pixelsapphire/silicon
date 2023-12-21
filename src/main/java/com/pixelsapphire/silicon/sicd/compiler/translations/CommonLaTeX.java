package com.pixelsapphire.silicon.sicd.compiler.translations;

import com.pixelsapphire.silicon.latex.LaTeXCommand;
import org.jetbrains.annotations.NotNull;

public class CommonLaTeX {

    public static @NotNull String dipChipLabel(@NotNull String label) {
        final var text = new LaTeXCommand("texttt").withRequiredArgument(label);
        final var rotateBox = new LaTeXCommand("rotatebox").withOptionalArgument("origin", "c").withRequiredArgument("-90");
        return rotateBox.withRequiredArgument(text).translate();
    }
}
