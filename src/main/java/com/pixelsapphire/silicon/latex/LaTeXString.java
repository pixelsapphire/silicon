package com.pixelsapphire.silicon.latex;

import org.jetbrains.annotations.NotNull;

public class LaTeXString implements LaTeX {

    private final String text;

    public LaTeXString(@NotNull String text) {
        this.text = text.replace("\\", "\\textbackslash").replace("$", "\\$")
                        .replace("%", "\\%").replace("&", "\\&")
                        .replace("#", "\\#").replace("_", "\\_")
                        .replace("{", "\\{").replace("}", "\\}")
                        .replace("~", "\\textasciitilde").replace("^", "\\textasciicircum");
    }

    @Override
    public @NotNull String translate() {
        return text;
    }
}
