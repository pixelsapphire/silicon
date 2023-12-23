package com.pixelsapphire.silicon.sicd.compiler.translations;

import com.pixelsapphire.silicon.latex.LaTeX;
import com.pixelsapphire.silicon.latex.LaTeXCommand;
import com.pixelsapphire.silicon.latex.LaTeXMathMode;
import com.pixelsapphire.silicon.sicd.parser.node.literal.LiteralNode;
import com.pixelsapphire.silicon.sicd.parser.node.literal.StringLiteralNode;
import org.jetbrains.annotations.NotNull;

public class CommonLaTeX {

    public static @NotNull String makeDipChipLabel(@NotNull String label) {
        final var text = new LaTeXCommand("texttt").withRequiredArgument(label);
        final var rotateBox = new LaTeXCommand("rotatebox").withOptionalArgument("origin", "c").withRequiredArgument("-90");
        return rotateBox.withRequiredArgument(text).translate();
    }

    public static @NotNull String makePinLabel(@NotNull LiteralNode pin, int rotation) {
        LaTeX pinLabel = new LaTeXCommand("texttt").withRequiredArgument(pin.getLiteral());
        if (pin instanceof final StringLiteralNode name && name.isNegated())
            pinLabel = pinLabel.wrappedWith(new LaTeXCommand("overline")).wrappedWith(LaTeXMathMode.inline());
        pinLabel = pinLabel.wrappedWith(new LaTeXCommand("scriptsize"))
                           .wrappedWith(new LaTeXCommand("rotatebox").withOptionalArgument("origin", "c")
                                                                     .withRequiredArgument("" + rotation));
        return pinLabel.translate();
    }
}
