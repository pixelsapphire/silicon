package com.pixelsapphire.silicon.latex;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class LaTeXMathMode extends LaTeXWrapper {

    private final boolean isInline;
    private LaTeX content;

    private LaTeXMathMode(boolean inline) {
        this.isInline = inline;
    }

    @Contract("-> new")
    public static @NotNull LaTeXMathMode inline() {
        return new LaTeXMathMode(true);
    }

    @Contract("-> new")
    public static @NotNull LaTeXMathMode display() {
        return new LaTeXMathMode(false);
    }

    public void setContent(@NotNull LaTeX content) {
        this.content = content;
    }

    public void setContent(@NotNull String content) {
        setContent(new LaTeXString(content));
    }

    @Contract("_ -> this")
    public LaTeXMathMode withContent(@NotNull LaTeX content) {
        setContent(content);
        return this;
    }

    @Contract("_ -> this")
    public LaTeXMathMode withContent(@NotNull String content) {
        return withContent(new LaTeXString(content));
    }

    @Override
    public @NotNull String translate() {
        return (isInline ? "$" : "$$") + content.translate() + (isInline ? "$" : "$$");
    }

    @Override
    protected @NotNull LaTeXMathMode wrap(@NotNull LaTeX latex) {
        return withContent(latex);
    }
}
